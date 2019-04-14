//package com.codefans.calc.engine;
//
//import com.lejr.credit.common.calc.bean.FormulaVariables;
//import com.lejr.credit.common.calc.util.AnalysisToolUtil;
//import com.lejr.credit.common.exception.CreditException;
//import com.lejr.credit.productcenter.domain.dto.ProductInstanceDTO;
//import com.lejr.credit.productcenter.domain.dto.ProductInstanceFeeDTO;
//import com.lejr.credit.productcenter.domain.enums.CycleTypeEnum;
//import com.lejr.credit.productcenter.domain.enums.ProductResultCode;
//import com.lejr.credit.productcenter.domain.enums.SubjectEnum;
//import com.lejr.credit.productcenter.engine.bean.RepaymentPlanMeasureBean;
//import com.lejr.credit.productcenter.engine.bean.SubjectRepaymentPlanBean;
//import com.lejr.credit.productcenter.engine.util.ProductUtil;
//import net.sf.json.JSONObject;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * 提前还款手续费计算引擎
// * @author yanxiaobo
// *
// */
//@Service("RECPFEE")
//public class RECPFEECalculateEngine implements CalculateEngine {
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
//		List<SubjectRepaymentPlanBean> pfeeRepaymentPlanList = new ArrayList<>();
//		SubjectRepaymentPlanBean pfeeRepaymentPlan = new SubjectRepaymentPlanBean();
//
//		//当期费用
//		BigDecimal amortizedFee = calcAmortizedFee(measureBean, productSubject);
//
//		pfeeRepaymentPlan.setCharge(amortizedFee);
//		pfeeRepaymentPlan.setChargeFormula(productSubject.getCalculationFormula());
//		pfeeRepaymentPlan.setNextRepayDate(null);
//		pfeeRepaymentPlan.setChargeType(SubjectEnum.RECPFEE.getName());
//		pfeeRepaymentPlan.setChargeName(SubjectEnum.RECPFEE.getDesc());
//
//		String cycleType = productSubject.getFeeCycleType();
//		if (cycleType.equals(CycleTypeEnum.FORMER.getName())) {
//			pfeeRepaymentPlan.setRepaymentDate(measureBean.getRepaymentPlanChangeDate());
//			pfeeRepaymentPlan.setStartDate(null);
//			pfeeRepaymentPlan.setEndDate(null);
//			pfeeRepaymentPlan.setPeriod(0);
//		} else {
//			throw new CreditException(ProductResultCode.NO_SUPPORT_CYCLETYPE);
//		}
//		pfeeRepaymentPlanList.add(pfeeRepaymentPlan);
//
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
//			RepaymentPlanMeasureBean measureBean, ProductInstanceFeeDTO productSubject) {
//		FormulaVariables formulaVariables = new FormulaVariables();
//		formulaVariables.setRp(measureBean.getRemainingPrincipal().toString());
//		formulaVariables.setFirr(productSubject.getValue());
//		BigDecimal fee = AnalysisToolUtil.calculationEntrance(
//				productSubject.getCalculationFormula(), JSONObject.fromObject(formulaVariables));
//
//		return fee;
//	}
//
//
//}
