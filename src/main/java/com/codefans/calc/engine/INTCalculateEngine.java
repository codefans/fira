//package com.codefans.calc.engine;
//
//import com.lejr.credit.common.calc.bean.FormulaVariables;
//import com.lejr.credit.common.calc.bean.TimeIntervalBean;
//import com.lejr.credit.common.calc.util.AnalysisToolUtil;
//import com.lejr.credit.common.calc.util.CalculateEngineUtil;
//import com.lejr.credit.common.exception.CreditException;
//import com.lejr.credit.productcenter.domain.dto.ProductInstanceDTO;
//import com.lejr.credit.productcenter.domain.dto.ProductInstanceFeeDTO;
//import com.lejr.credit.productcenter.domain.enums.CycleTypeEnum;
//import com.lejr.credit.productcenter.domain.enums.ProductResultCode;
//import com.lejr.credit.productcenter.domain.enums.SubjectEnum;
//import com.lejr.credit.productcenter.engine.bean.RepaymentPlanMeasureBean;
//import com.lejr.credit.productcenter.engine.bean.SubjectRepaymentPlanBean;
//import com.lejr.credit.productcenter.engine.util.ProductUtil;
//import com.lejr.credit.productcenter.engine.util.TimeIntervalUtil;
//import net.sf.json.JSONObject;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//
///**
// * 利息计算引擎
// *
// */
//public class INTCalculateEngine implements CalculateEngine{
//
//	@Override
//	public List<SubjectRepaymentPlanBean> execute(RepaymentPlanMeasureBean measureBean) {
//		ProductInstanceDTO productInfo = measureBean.getProductInstance();
//		ProductInstanceFeeDTO productSubject = ProductUtil.findProductSubject(
//				productInfo, SubjectEnum.INT, measureBean.getRepaymentWay());
//		if (productSubject == null || Double.valueOf(productSubject.getValue()) <= 0) {
//			return Collections.emptyList();
//		}
//
//		String cycleType = productSubject.getFeeCycleType();
//		//生成时间周期
//		List<TimeIntervalBean> timeTntervalList = getTimeIntervalList(measureBean, cycleType);
//
//		if(cycleType.equals(CycleTypeEnum.FORMER_L.getName()) ||
//				cycleType.equals(CycleTypeEnum.LAST.getName())) {
//			//利息预收 & 最后一次性收取
//			return getRepaymentPlanListForLast(measureBean, productSubject, timeTntervalList);
//
//		} else if (cycleType.equals(CycleTypeEnum.MONTH.getName()) ||
//				cycleType.equals(CycleTypeEnum.SEASON.getName())) {
//			//按月
//			return getRepaymentPlanListForMonth(measureBean, productSubject, timeTntervalList);
//		} else {
//
//			throw new CreditException(ProductResultCode.NO_SUPPORT_CYCLETYPE);
//		}
//	}
//
//	/**
//	 * 生成时间周期
//	 * @param measureBean
//	 * @return
//	 */
//	private List<TimeIntervalBean> getTimeIntervalList(RepaymentPlanMeasureBean measureBean, String cycleType) {
//		ProductInstanceDTO product = measureBean.getProductInstance();
//		Date lenderDate = measureBean.getLenderDate();
//		Date maturityDate = measureBean.getMaturityDate();
//		int repaymentDate = ProductUtil.getRepaymentDate(product, lenderDate);
//
//		List<TimeIntervalBean> timeTntervalList = null;
//
//		//固定分期数
//		if (measureBean.getAging() != null) {
//			timeTntervalList = CalculateEngineUtil.cuttingTimeIntervalByMonth(lenderDate, measureBean.getAging());
//			return timeTntervalList;
//		}
//
//		if (cycleType.equals(CycleTypeEnum.FORMER_L.getName()) ||
//				cycleType.equals(CycleTypeEnum.LAST.getName())) {
//			timeTntervalList = CalculateEngineUtil.cuttingTimeIntervalByLast(repaymentDate, lenderDate, maturityDate);
//		} else if (cycleType.equals(CycleTypeEnum.MONTH.getName())) {
//			timeTntervalList = CalculateEngineUtil.cuttingTimeIntervalByMonth(repaymentDate, lenderDate, maturityDate);
//		} else if (cycleType.equals(CycleTypeEnum.SEASON.getName())) {
//			timeTntervalList = CalculateEngineUtil.cuttingTimeIntervalBySeason(repaymentDate, lenderDate, maturityDate);
//		} else {
//			throw new CreditException(ProductResultCode.NO_SUPPORT_CYCLETYPE);
//		}
//
//		return timeTntervalList;
//	}
//
//	/**
//	 * 生成利息还款计划：最后一次性收取（含预收）
//	 * @param measureBean
//	 * @return
//	 */
//	private List<SubjectRepaymentPlanBean> getRepaymentPlanListForLast(
//			RepaymentPlanMeasureBean measureBean, ProductInstanceFeeDTO productSubject,
//			List<TimeIntervalBean> timeTntervalList) {
//		List<SubjectRepaymentPlanBean> priRepaymentPlanList = measureBean.getPriRepaymentPlanList();
//		List<SubjectRepaymentPlanBean> intRepaymentPlanList = new ArrayList<>();
//
//		//当前时间周期
//		TimeIntervalBean timeIntervalBean = timeTntervalList.get(0);
//		//当前本金计划
//		SubjectRepaymentPlanBean priRepaymentPlan = priRepaymentPlanList.get(priRepaymentPlanList.size() - 1);
//		//当期利息
//		BigDecimal amortizedInterest = calcAmortizedInterest(priRepaymentPlan, timeIntervalBean, productSubject);
//
//		BigDecimal intDeposit = BigDecimal.ZERO;
//		//计算预收息
//		String cycleType = productSubject.getFeeCycleType();
//		if (cycleType.equals(CycleTypeEnum.FORMER_L.getName())) {
//			//核心企业审核日
//			Date coreEnterpriseAuditDay = measureBean.getCoreEnterpriseAuditDay();
//			long cc = (coreEnterpriseAuditDay.getTime()-timeIntervalBean.getStartDate().getTime())/(24*60*60*1000);
//			long nc = 0;
//			long dd = TimeIntervalUtil.getDaysLong(timeIntervalBean);
//			intDeposit = BigDecimal.valueOf(Double.valueOf(cc)/Double.valueOf((dd-nc)))
//					.multiply(amortizedInterest).setScale(2, RoundingMode.HALF_UP);
//			if (intDeposit.compareTo(BigDecimal.ZERO) <= 0) {
//				intDeposit = BigDecimal.ZERO;
//			}
//			SubjectRepaymentPlanBean intDepositRepaymentPlan = new SubjectRepaymentPlanBean();
//			intDepositRepaymentPlan.setCharge(intDeposit);
//			intDepositRepaymentPlan.setChargeFormula(productSubject.getCalculationFormula());
//			intDepositRepaymentPlan.setChargeType(SubjectEnum.INT.getName());
//			intDepositRepaymentPlan.setChargeName(SubjectEnum.INT.getDesc());
//			intDepositRepaymentPlan.setNextRepayDate(null);
//			intDepositRepaymentPlan.setRepaymentDate(measureBean.getLenderDate());
//			intDepositRepaymentPlan.setStartDate(null);
//			intDepositRepaymentPlan.setEndDate(null);
//			intDepositRepaymentPlan.setPeriod(0);
//			intRepaymentPlanList.add(intDepositRepaymentPlan);
//		}
//
//		SubjectRepaymentPlanBean intRepaymentPlan = new SubjectRepaymentPlanBean();
//		intRepaymentPlan.setCharge(amortizedInterest.subtract(intDeposit));
//		intRepaymentPlan.setChargeFormula(productSubject.getCalculationFormula());
//		intRepaymentPlan.setChargeType(SubjectEnum.INT.getName());
//		intRepaymentPlan.setChargeName(SubjectEnum.INT.getDesc());
//		intRepaymentPlan.setRepaymentDate(timeIntervalBean.getRepaymentDate());
//		intRepaymentPlan.setStartDate(timeIntervalBean.getStartDate());
//		intRepaymentPlan.setEndDate(timeIntervalBean.getEndDate());
//		intRepaymentPlan.setPeriod(1);
//		intRepaymentPlanList.add(intRepaymentPlan);
//
//		measureBean.setTotalInt(amortizedInterest);
//		return intRepaymentPlanList;
//	}
//
//	/**
//	 * 生成利息还款计划：按月
//	 * @param measureBean
//	 * @param productSubject
//	 * @param timeTntervalList
//	 * @return
//	 */
//	private List<SubjectRepaymentPlanBean> getRepaymentPlanListForMonth(
//			RepaymentPlanMeasureBean measureBean, ProductInstanceFeeDTO productSubject,
//			List<TimeIntervalBean> timeTntervalList) {
//		List<SubjectRepaymentPlanBean> priRepaymentPlanList = measureBean.getPriRepaymentPlanList();
//		List<SubjectRepaymentPlanBean> intRepaymentPlanList = new ArrayList<>();
//
//		//本金计划位置
//		int priPos = 0;
//		BigDecimal totalInt = BigDecimal.ZERO;
//		for (int i=1; i<=timeTntervalList.size(); i++) {
//			//当前时间周期
//			TimeIntervalBean timeIntervalBean = timeTntervalList.get(i - 1);
//			//当前本金计划
//			SubjectRepaymentPlanBean priRepaymentPlan = priRepaymentPlanList.get(priPos);
//			if (priRepaymentPlan.getRepaymentDate().compareTo(timeIntervalBean.getRepaymentDate()) == 0) {
//				priPos++;
//			}
//			//当期利息
//			BigDecimal amortizedInterest = calcAmortizedInterest(priRepaymentPlan, timeIntervalBean, productSubject);
//
//			SubjectRepaymentPlanBean intRepaymentPlan = new SubjectRepaymentPlanBean();
//			intRepaymentPlan.setCharge(amortizedInterest);
//			intRepaymentPlan.setChargeFormula(productSubject.getCalculationFormula());
//			intRepaymentPlan.setChargeType(SubjectEnum.INT.getName());
//			intRepaymentPlan.setChargeName(SubjectEnum.INT.getDesc());
//			if (i != timeTntervalList.size()) {
//				intRepaymentPlan.setNextRepayDate(timeTntervalList.get(i).getRepaymentDate());
//			}
//			intRepaymentPlan.setRepaymentDate(timeIntervalBean.getRepaymentDate());
//			intRepaymentPlan.setStartDate(timeIntervalBean.getStartDate());
//			intRepaymentPlan.setEndDate(timeIntervalBean.getEndDate());
//			intRepaymentPlan.setPeriod(i);
//			intRepaymentPlanList.add(intRepaymentPlan);
//
//			totalInt.add(amortizedInterest);
//		}
//
//		measureBean.setTotalInt(totalInt);
//		return intRepaymentPlanList;
//	}
//
//	/**
//	 * 计算当期利息
//	 * @param priRepaymentPlan
//	 * @param timeIntervalBean
//	 * @param productSubject
//	 * @return
//	 */
//	private BigDecimal calcAmortizedInterest(SubjectRepaymentPlanBean priRepaymentPlan,
//			TimeIntervalBean timeIntervalBean, ProductInstanceFeeDTO productSubject) {
//		long nc = 0;
//		long dd = TimeIntervalUtil.getDaysLong(timeIntervalBean);
//
//		FormulaVariables formulaVariables = new FormulaVariables();
//		formulaVariables.setY(priRepaymentPlan.getCalculatePrincipal().toString());
//		formulaVariables.setIrr(productSubject.getValue()); //TODO 处理浮动利率
//		formulaVariables.setNc(String.valueOf(nc));
//		formulaVariables.setDd(String.valueOf(dd));
//		BigDecimal amortizedInterest = AnalysisToolUtil.calculationEntrance(
//				productSubject.getCalculationFormula(), JSONObject.fromObject(formulaVariables));
//		return amortizedInterest;
//	}
//}
