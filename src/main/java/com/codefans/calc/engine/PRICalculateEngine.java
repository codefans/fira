package com.codefans.calc.engine;

import com.codefans.domain.DateInterval;
import com.codefans.domain.FormulaVariables;
import com.codefans.domain.SubjectRepaymentPlan;
import com.codefans.domain.param.CalculateParam;
import com.codefans.enums.CycleTypeEnum;
import com.codefans.parser.SciJavaJepParser;
import com.codefans.util.BeanUtils;
import com.codefans.util.CalculateEngineUtil;
import com.codefans.util.DateUtils;
import com.codefans.util.EntityUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 本金计算引擎
 *
 */
public class PRICalculateEngine implements CalculateEngine {

	@Override
	public List<SubjectRepaymentPlan> execute(CalculateParam calculateParam) {
		if (calculateParam == null) {
			return Collections.emptyList();
		}

		String cycleType = calculateParam.getCycleType();
		Integer periods = calculateParam.getPeriods();
		Date grantDate = calculateParam.getGrantDate();
		Date expiriedDate = calculateParam.getExpiriedDate();
		Integer repaymentDay = calculateParam.getRepaymentDay();

		//生成时间周期
		List<DateInterval> dateTntervalList = null;
		try {
			dateTntervalList = getDateIntervalList(cycleType, periods, grantDate, expiriedDate, repaymentDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		BigDecimal calculatePrincipal = calculateParam.getCalculateAmount();
		String calculateRate = calculateParam.getCalculateRate();
		String periodsStr = periods + "";
		String calculationFormula = calculateParam.getCalculationFormula();

		return getIntRepaymentPlanListForLast(calculatePrincipal, calculateRate, periodsStr, calculationFormula, dateTntervalList);

	}

	/**
	 * 生成时间周期
	 * @param cycleType
	 * @param periods
	 * @param grantDate
	 * @param expiredDate
	 * @param repaymentDay
	 * @return
	 * @throws ParseException
	 */
	private List<DateInterval> getDateIntervalList(String cycleType, int periods, Date grantDate, Date expiredDate, int repaymentDay) throws ParseException {

		List<DateInterval> dateTntervalList = null;

		//固定分期数
		if (periods > 0) {
			dateTntervalList = CalculateEngineUtil.generateDateIntervalByMonth(grantDate, periods);
			return dateTntervalList;
		}

		if (cycleType.equals(CycleTypeEnum.MONTH.getName())) {
			dateTntervalList = CalculateEngineUtil.generateDateIntervalByMonth(repaymentDay, grantDate, expiredDate);
		} else if(cycleType.equals(CycleTypeEnum.LAST.getName())) {
			dateTntervalList = CalculateEngineUtil.generateDateIntervalByLast(grantDate, expiredDate);
		} else {
			throw new RuntimeException("cycleType not support");
		}

		return dateTntervalList;
	}

	/**
	 * 生成本金还款计划
	 * @param calculatePrincipal
	 * @param priRate
	 * @param periods
	 * @param calculationFormula
	 * @param dateTntervalList
	 * @return
	 */
	private List<SubjectRepaymentPlan> getIntRepaymentPlanListForLast(
			BigDecimal calculatePrincipal,
			String priRate,
			String periods,
			String calculationFormula,
			List<DateInterval> dateTntervalList) {
		List<SubjectRepaymentPlan> priRepaymentPlanList = new ArrayList<>();

		//已还本金
		BigDecimal debt = BigDecimal.ZERO;
		//初始化计算本金
//		BigDecimal calculatePrincipal = null;
		for (int i=1; i<=dateTntervalList.size(); i++) {
			//当前时间周期
			DateInterval dateInterval = dateTntervalList.get(i - 1);

			SubjectRepaymentPlan priRepaymentPlan = new SubjectRepaymentPlan();
			FormulaVariables formulaVariables = new FormulaVariables();
			formulaVariables.setY(calculatePrincipal.toString());
			formulaVariables.setIrr(priRate);
			formulaVariables.setNc(dateInterval.getDays());
			formulaVariables.setX(periods);
			BigDecimal shouldPrincipal = SciJavaJepParser.calculationEntrance(
						calculationFormula, EntityUtils.objToMap(formulaVariables));

			if (i != dateTntervalList.size()) {
				priRepaymentPlan.setNextRepayDate(dateTntervalList.get(i).getRepaymentDate());
			} else {
				//最后一期本金=本金总额-已还本金
				shouldPrincipal = calculatePrincipal.subtract(debt);
			}
			//当期预计应还本金
			priRepaymentPlan.setShouldPrincipal(shouldPrincipal);
			debt = debt.add(shouldPrincipal);
			//当期预计剩余本金
			priRepaymentPlan.setRemainPrincipal(calculatePrincipal.subtract(debt));

			priRepaymentPlan.setPrincipalFormula(calculationFormula);

			priRepaymentPlan.setRepaymentDate(dateInterval.getRepaymentDate());
			priRepaymentPlan.setStartDate(dateInterval.getStartDate());
			priRepaymentPlan.setEndDate(dateInterval.getEndDate());
			priRepaymentPlan.setPeriod(i);

			priRepaymentPlan.setCalculatePrincipal(calculatePrincipal);

			priRepaymentPlanList.add(priRepaymentPlan);
		}
		return priRepaymentPlanList;
	}
}
