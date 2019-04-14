//package com.codefans.calc.engine;
//
//import com.lejr.credit.common.calc.bean.FormulaVariables;
//import com.lejr.credit.common.calc.bean.TimeIntervalBean;
//import com.lejr.credit.common.calc.util.AnalysisToolUtil;
//import com.lejr.credit.common.calc.util.CalculateEngineUtil;
//import com.lejr.credit.common.calc.util.MoneyUtil;
//import com.lejr.credit.common.exception.CreditException;
//import com.lejr.credit.productcenter.domain.dto.ProductInstanceDTO;
//import com.lejr.credit.productcenter.domain.dto.ProductInstanceFeeDTO;
//import com.lejr.credit.productcenter.domain.enums.CycleTypeEnum;
//import com.lejr.credit.productcenter.domain.enums.ProductResultCode;
//import com.lejr.credit.productcenter.domain.enums.SubjectEnum;
//import com.lejr.credit.productcenter.domain.enums.ValueTypeEnum;
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
///**
// * 服务费还款计划
// * @author yanxiaobo
// *
// */
//@Service("SFEE")
//public class SFEECalculateEngine implements CalculateEngine {
//
//	@Override
//	public List<SubjectRepaymentPlanBean> execute(RepaymentPlanMeasureBean measureBean) {
//		ProductInstanceDTO productInfo = measureBean.getProductInstance();
//		ProductInstanceFeeDTO productSubject = ProductUtil.findProductSubject(
//				productInfo, SubjectEnum.SFEE, measureBean.getRepaymentWay());
//		if (productSubject == null || Double.valueOf(productSubject.getValue()) <= 0) {
//			return Collections.emptyList();
//		}
//
//		String cycleType = productSubject.getFeeCycleType();
//		//生成时间周期
//		List<TimeIntervalBean> timeTntervalList = getTimeIntervalList(measureBean, cycleType);
//
//		if(cycleType.equals(CycleTypeEnum.FORMER_L.getName()) ||
//				cycleType.equals(CycleTypeEnum.FORMER.getName()) ||
//				cycleType.equals(CycleTypeEnum.LAST.getName())) {
//			//最后一次性收取（含预收）
//			return getRepaymentPlanListForLast(measureBean, productSubject, timeTntervalList);
//
//		} else if (cycleType.equals(CycleTypeEnum.MONTH.getName())) {
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
//		if (cycleType.equals(CycleTypeEnum.FORMER_L.getName()) ||
//				cycleType.equals(CycleTypeEnum.FORMER.getName()) ||
//				cycleType.equals(CycleTypeEnum.LAST.getName())) {
//			timeTntervalList = CalculateEngineUtil.cuttingTimeIntervalByLast(repaymentDate, lenderDate, maturityDate);
//		} else if (cycleType.equals(CycleTypeEnum.MONTH.getName())) {
//			timeTntervalList = CalculateEngineUtil.cuttingTimeIntervalByMonth(repaymentDate, lenderDate, maturityDate);
//		} else {
//			throw new CreditException(ProductResultCode.NO_SUPPORT_CYCLETYPE);
//		}
//
//		return timeTntervalList;
//	}
//
//	/**
//	 * 生成利息还款计划：最后一次性收取（含预收，部分预收）
//	 * @param measureBean
//	 * @return
//	 */
//	private List<SubjectRepaymentPlanBean> getRepaymentPlanListForLast(
//			RepaymentPlanMeasureBean measureBean, ProductInstanceFeeDTO productSubject,
//			List<TimeIntervalBean> timeTntervalList) {
//		List<SubjectRepaymentPlanBean> priRepaymentPlanList = measureBean.getPriRepaymentPlanList();
//		List<SubjectRepaymentPlanBean> sfeeRepaymentPlanList = new ArrayList<>();
//
//		//当前时间周期
//		TimeIntervalBean timeIntervalBean = timeTntervalList.get(0);
//		//当前本金计划
//		SubjectRepaymentPlanBean priRepaymentPlan = priRepaymentPlanList.get(priRepaymentPlanList.size() - 1);
//		//当期费用
//		BigDecimal amortizedFee = calcAmortizedFee(priRepaymentPlan, timeIntervalBean, productSubject);
//
//		//设置总服务费
//		measureBean.setTotalSfee(amortizedFee);
//
//		BigDecimal sfeeDeposit = BigDecimal.ZERO;
//		//计算预收费用
//		String cycleType = productSubject.getFeeCycleType();
//		if (cycleType.equals(CycleTypeEnum.FORMER.getName())) {
//			SubjectRepaymentPlanBean sfeeDepositRepaymentPlan = new SubjectRepaymentPlanBean();
//			sfeeDepositRepaymentPlan.setCharge(amortizedFee);
//			sfeeDepositRepaymentPlan.setChargeFormula(productSubject.getCalculationFormula());
//			sfeeDepositRepaymentPlan.setChargeType(SubjectEnum.SFEE.getName());
//			sfeeDepositRepaymentPlan.setChargeName(SubjectEnum.SFEE.getDesc());
//			sfeeDepositRepaymentPlan.setNextRepayDate(null);
//			sfeeDepositRepaymentPlan.setRepaymentDate(measureBean.getLenderDate());
//			sfeeDepositRepaymentPlan.setStartDate(null);
//			sfeeDepositRepaymentPlan.setEndDate(null);
//			sfeeDepositRepaymentPlan.setPeriod(0);
//			sfeeRepaymentPlanList.add(sfeeDepositRepaymentPlan);
//			return sfeeRepaymentPlanList;
//
//		} else if (cycleType.equals(CycleTypeEnum.FORMER_L.getName())) {
//			//核心企业审核日
//			Date coreEnterpriseAuditDay = measureBean.getCoreEnterpriseAuditDay();
//			long cc = (coreEnterpriseAuditDay.getTime()-timeIntervalBean.getStartDate().getTime())/(24*60*60*1000);
//			long nc = 0;
//			long dd = TimeIntervalUtil.getDaysLong(timeIntervalBean);
//			sfeeDeposit = BigDecimal.valueOf(Double.valueOf(cc)/Double.valueOf((dd-nc)))
//					.multiply(amortizedFee).setScale(2, RoundingMode.HALF_UP);
//			if (sfeeDeposit.compareTo(BigDecimal.ZERO) <= 0) {
//				sfeeDeposit = BigDecimal.ZERO;
//			}
//			SubjectRepaymentPlanBean sfeeDepositRepaymentPlan = new SubjectRepaymentPlanBean();
//			sfeeDepositRepaymentPlan.setCharge(sfeeDeposit);
//			sfeeDepositRepaymentPlan.setChargeFormula(productSubject.getCalculationFormula());
//			sfeeDepositRepaymentPlan.setChargeType(SubjectEnum.SFEE.getName());
//			sfeeDepositRepaymentPlan.setChargeName(SubjectEnum.SFEE.getDesc());
//			sfeeDepositRepaymentPlan.setNextRepayDate(null);
//			sfeeDepositRepaymentPlan.setRepaymentDate(measureBean.getLenderDate());
//			sfeeDepositRepaymentPlan.setStartDate(null);
//			sfeeDepositRepaymentPlan.setEndDate(null);
//			sfeeDepositRepaymentPlan.setPeriod(0);
//			sfeeRepaymentPlanList.add(sfeeDepositRepaymentPlan);
//		}
//
//		SubjectRepaymentPlanBean sfeeRepaymentPlan = new SubjectRepaymentPlanBean();
//		sfeeRepaymentPlan.setCharge(amortizedFee.subtract(sfeeDeposit));
//		sfeeRepaymentPlan.setChargeFormula(productSubject.getCalculationFormula());
//		sfeeRepaymentPlan.setChargeType(SubjectEnum.SFEE.getName());
//		sfeeRepaymentPlan.setChargeName(SubjectEnum.SFEE.getDesc());
//		sfeeRepaymentPlan.setRepaymentDate(timeIntervalBean.getRepaymentDate());
//		sfeeRepaymentPlan.setStartDate(timeIntervalBean.getStartDate());
//		sfeeRepaymentPlan.setEndDate(timeIntervalBean.getEndDate());
//		sfeeRepaymentPlan.setPeriod(1);
//		sfeeRepaymentPlanList.add(sfeeRepaymentPlan);
//		return sfeeRepaymentPlanList;
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
//		List<SubjectRepaymentPlanBean> sfeeRepaymentPlanList = new ArrayList<>();
//
//		//总服务费
//		BigDecimal totalSfee = BigDecimal.ZERO;
//
//		//本金计划位置
//		int priPos = 0;
//		for (int i=1; i<=timeTntervalList.size(); i++) {
//			//当前时间周期
//			TimeIntervalBean timeIntervalBean = timeTntervalList.get(i - 1);
//			//当前本金计划
//			SubjectRepaymentPlanBean priRepaymentPlan = priRepaymentPlanList.get(priPos);
//			if (priRepaymentPlan.getRepaymentDate().compareTo(timeIntervalBean.getRepaymentDate()) == 0) {
//				priPos++;
//			}
//			//当期费用
//			BigDecimal amortizedFee = calcAmortizedFee(priRepaymentPlan, timeIntervalBean, productSubject);
//
//			SubjectRepaymentPlanBean sfeeRepaymentPlan = new SubjectRepaymentPlanBean();
//			sfeeRepaymentPlan.setCharge(amortizedFee);
//			sfeeRepaymentPlan.setChargeFormula(productSubject.getCalculationFormula());
//			sfeeRepaymentPlan.setChargeType(SubjectEnum.SFEE.getName());
//			sfeeRepaymentPlan.setChargeName(SubjectEnum.SFEE.getDesc());
//			if (i != timeTntervalList.size()) {
//				sfeeRepaymentPlan.setNextRepayDate(timeTntervalList.get(i).getRepaymentDate());
//			}
//			sfeeRepaymentPlan.setRepaymentDate(timeIntervalBean.getRepaymentDate());
//			sfeeRepaymentPlan.setStartDate(timeIntervalBean.getStartDate());
//			sfeeRepaymentPlan.setEndDate(timeIntervalBean.getEndDate());
//			sfeeRepaymentPlan.setPeriod(i);
//			sfeeRepaymentPlanList.add(sfeeRepaymentPlan);
//
//			totalSfee.add(amortizedFee);
//		}
//
//		measureBean.setTotalSfee(totalSfee);
//		return sfeeRepaymentPlanList;
//	}
//
//	/**
//	 * 计算当期利息
//	 * @param priRepaymentPlan
//	 * @param timeIntervalBean
//	 * @param productSubject
//	 * @return
//	 */
//	private BigDecimal calcAmortizedFee(SubjectRepaymentPlanBean priRepaymentPlan,
//			TimeIntervalBean timeIntervalBean, ProductInstanceFeeDTO productSubject) {
//		//固定值
//		if (ValueTypeEnum.FIXED.getName().equals(productSubject.getValueType())) {
//			return MoneyUtil.fenToYuan(Long.parseLong(productSubject.getValue()));
//		}
//
//		long nc = 0;
//		long dd = TimeIntervalUtil.getDaysLong(timeIntervalBean);
//
//		FormulaVariables formulaVariables = new FormulaVariables();
//		formulaVariables.setY(priRepaymentPlan.getCalculatePrincipal().toString());
//		formulaVariables.setFirr(productSubject.getValue());
//		formulaVariables.setNc(String.valueOf(nc));
//		formulaVariables.setDd(String.valueOf(dd));
//		BigDecimal amortizedInterest = AnalysisToolUtil.calculationEntrance(
//				productSubject.getCalculationFormula(), JSONObject.fromObject(formulaVariables));
//		return amortizedInterest;
//	}
//
//}
