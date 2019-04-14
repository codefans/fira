//package com.codefans.calc.engine;
//
//import com.lejr.credit.common.calc.bean.FormulaVariables;
//import com.lejr.credit.common.calc.bean.TimeIntervalBean;
//import com.lejr.credit.common.calc.util.AnalysisToolUtil;
//import com.lejr.credit.common.calc.util.CalculateEngineUtil;
//import com.lejr.credit.common.calc.util.MoneyUtil;
//import com.lejr.credit.productcenter.domain.dto.ProductInstanceDTO;
//import com.lejr.credit.productcenter.domain.dto.ProductInstanceFeeDTO;
//import com.lejr.credit.productcenter.domain.enums.CycleTypeEnum;
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
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
///**
// * 手续费计算引擎
// * @author yanxiaobo
// *
// */
//@Service("PFEE")
//public class PFEECalculateEngine implements CalculateEngine {
//
//	@Override
//	public List<SubjectRepaymentPlanBean> execute(RepaymentPlanMeasureBean measureBean) {
//		ProductInstanceDTO productInfo = measureBean.getProductInstance();
//		ProductInstanceFeeDTO productSubject = ProductUtil.findProductSubject(
//				productInfo, SubjectEnum.PFEE, measureBean.getRepaymentWay());
//		if (productSubject == null || Double.valueOf(productSubject.getValue()) <= 0) {
//			return Collections.emptyList();
//		}
//
//		List<SubjectRepaymentPlanBean> pfeeRepaymentPlanList = new ArrayList<>();
//		SubjectRepaymentPlanBean pfeeRepaymentPlan = new SubjectRepaymentPlanBean();
//
//		String cycleType = productSubject.getFeeCycleType();
//		Date lenderDate = measureBean.getLenderDate();
//		Date maturityDate = measureBean.getMaturityDate();
//
//		//当期费用
//		List<TimeIntervalBean> timeTntervalList = CalculateEngineUtil.cuttingTimeIntervalByLast(
//				0, lenderDate, maturityDate);
//		BigDecimal amortizedFee = calcAmortizedFee(measureBean, timeTntervalList.get(0), productSubject);
//
//		//设置总手续费
//		measureBean.setTotalPfee(amortizedFee);
//
//		pfeeRepaymentPlan.setCharge(amortizedFee);
//		pfeeRepaymentPlan.setChargeFormula(productSubject.getCalculationFormula());
//		pfeeRepaymentPlan.setNextRepayDate(null);
//
//
//		if (cycleType.equals(CycleTypeEnum.FORMER.getName())) {
//			pfeeRepaymentPlan.setStartDate(lenderDate);
//			pfeeRepaymentPlan.setEndDate(lenderDate);
//			pfeeRepaymentPlan.setRepaymentDate(lenderDate);
//			pfeeRepaymentPlan.setPeriod(0);
//		}
//		if (cycleType.equals(CycleTypeEnum.LAST.getName())) {
//			pfeeRepaymentPlan.setStartDate(lenderDate);
//			pfeeRepaymentPlan.setEndDate(maturityDate);
//			pfeeRepaymentPlan.setRepaymentDate(maturityDate);
//			//pfeeRepaymentPlan.setPeriod(priRepaymentPlanList.get(priRepaymentPlanList.size() - 1).getPeriod());
//			pfeeRepaymentPlan.setPeriod(1);
//		}
//
//		pfeeRepaymentPlan.setChargeType(SubjectEnum.PFEE.getName());
//		pfeeRepaymentPlan.setChargeName(SubjectEnum.PFEE.getDesc());
//		pfeeRepaymentPlanList.add(pfeeRepaymentPlan);
//		return pfeeRepaymentPlanList;
//	}
//
//	/**
//	 * 费用计算
//	 * @param measureBean
//	 * @param productSubject
//	 * @return
//	 */
//	private BigDecimal calcAmortizedFee(
//			RepaymentPlanMeasureBean measureBean, TimeIntervalBean timeIntervalBean,
//			ProductInstanceFeeDTO productSubject) {
//		//固定值
//		if (ValueTypeEnum.FIXED.getName().equals(productSubject.getValueType())) {
//			return MoneyUtil.fenToYuan(Long.parseLong(productSubject.getValue()));
//		}
//
//		long nc = 0;
//		long dd = TimeIntervalUtil.getDaysLong(timeIntervalBean);
//
//		FormulaVariables formulaVariables = new FormulaVariables();
//		formulaVariables.setY(measureBean.getContractMoney().toString());
//		formulaVariables.setFirr(productSubject.getValue());
//		formulaVariables.setNc(String.valueOf(nc));
//		formulaVariables.setDd(String.valueOf(dd));
//		BigDecimal fee = AnalysisToolUtil.calculationEntrance(
//				productSubject.getCalculationFormula(), JSONObject.fromObject(formulaVariables));
//
//		return fee;
//	}
//
//}
