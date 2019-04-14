package com.codefans.domain.param;

import com.codefans.domain.AdvanceFeePlan;
import com.codefans.domain.PeriodFeePlan;
import com.codefans.domain.SubjectRepaymentPlan;
import com.codefans.enums.SubjectEnum;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: codefans
 * @Date: 2019-04-13 23:49
 */

public class CalculateParam {

    /************************输入参数**************************/
    /**
     * 还款方式
     */
    private String repaymentType;

    /**
     *
     */
    private String cycleType;

    /**
     * 贷款发生日期，yyyy-MM-dd
     */
    private Date grantDate;
    /**
     * 贷款到期日期，yyyy-MM-dd
     */
    private Date expiriedDate;

    /**
     * 还款日
     */
    private Integer repaymentDay;

    /**
     * 固定分期数
     */
    private Integer periods;

    /**
     * 合同金额
     */
    private BigDecimal contractAmount;
    /**
     * 试算类型
     */
    private String trialType;
    /**
     * 还款计划变更方式
     */
    private String changeWay;
    /**
     * 剩余本金
     */
    private BigDecimal remainingPrincipal;

    /**
     * 还款计划变更日期
     */
    private Date changeDate;
    /**
     * 测算金额
     */
    private BigDecimal calculateAmount;

    /**
     * 测算利率
     */
    String calculateRate;

    /**
     * 计算公式
     */
    String calculationFormula;

    /**
     * 核心企业审核日
     */
    private Date coreEnterpriseAuditDay;

    /************************分科目还款计划**************************/
    /**
     * 本金还款计划
     */
    private List<SubjectRepaymentPlan> priRepaymentPlanList;

    /**
     * 利息还款计划
     */
    private List<SubjectRepaymentPlan> intRepaymentPlanList;

    /**
     * 服务费还款计划
     */
    private List<SubjectRepaymentPlan> sfeeRepaymentPlanList;

    /**
     * 手续费还款计划
     */
    private List<SubjectRepaymentPlan> pfeeRepaymentPlanList;

    /************************预收费用计划**************************/
    /**
     * 已出趸交费用
     */
    private BigDecimal paymentFee;

    /**
     * 趸交计划列表
     */
    private List<AdvanceFeePlan> advanceFeePlanList;

    /************************分期信息**************************/
    /**
     * 设置最大分期数
     */
    private Integer maxPeriod;

    /**
     * 设置最大分期数科目
     */
    private SubjectEnum maxPeriodSubject;

    /**
     * 利息总计
     */
    private BigDecimal totalInt;

    /**
     * 手续费总计
     */
    private BigDecimal totalPfee;

    /**
     * 服务费总计
     */
    private BigDecimal totalSfee;

    /**
     * 分期计划列表
     */
    private List<PeriodFeePlan> periodPlanList;

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public Date getGrantDate() {
        return grantDate;
    }

    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }

    public Date getExpiriedDate() {
        return expiriedDate;
    }

    public void setExpiriedDate(Date expiriedDate) {
        this.expiriedDate = expiriedDate;
    }

    public Integer getRepaymentDay() {
        return repaymentDay;
    }

    public void setRepaymentDay(Integer repaymentDay) {
        this.repaymentDay = repaymentDay;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getTrialType() {
        return trialType;
    }

    public void setTrialType(String trialType) {
        this.trialType = trialType;
    }

    public String getChangeWay() {
        return changeWay;
    }

    public void setChangeWay(String changeWay) {
        this.changeWay = changeWay;
    }

    public BigDecimal getRemainingPrincipal() {
        return remainingPrincipal;
    }

    public void setRemainingPrincipal(BigDecimal remainingPrincipal) {
        this.remainingPrincipal = remainingPrincipal;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public BigDecimal getCalculateAmount() {
        return calculateAmount;
    }

    public void setCalculateAmount(BigDecimal calculateAmount) {
        this.calculateAmount = calculateAmount;
    }

    public Date getCoreEnterpriseAuditDay() {
        return coreEnterpriseAuditDay;
    }

    public void setCoreEnterpriseAuditDay(Date coreEnterpriseAuditDay) {
        this.coreEnterpriseAuditDay = coreEnterpriseAuditDay;
    }

    public List<SubjectRepaymentPlan> getPriRepaymentPlanList() {
        return priRepaymentPlanList;
    }

    public void setPriRepaymentPlanList(List<SubjectRepaymentPlan> priRepaymentPlanList) {
        this.priRepaymentPlanList = priRepaymentPlanList;
    }

    public List<SubjectRepaymentPlan> getIntRepaymentPlanList() {
        return intRepaymentPlanList;
    }

    public void setIntRepaymentPlanList(List<SubjectRepaymentPlan> intRepaymentPlanList) {
        this.intRepaymentPlanList = intRepaymentPlanList;
    }

    public List<SubjectRepaymentPlan> getSfeeRepaymentPlanList() {
        return sfeeRepaymentPlanList;
    }

    public void setSfeeRepaymentPlanList(List<SubjectRepaymentPlan> sfeeRepaymentPlanList) {
        this.sfeeRepaymentPlanList = sfeeRepaymentPlanList;
    }

    public List<SubjectRepaymentPlan> getPfeeRepaymentPlanList() {
        return pfeeRepaymentPlanList;
    }

    public void setPfeeRepaymentPlanList(List<SubjectRepaymentPlan> pfeeRepaymentPlanList) {
        this.pfeeRepaymentPlanList = pfeeRepaymentPlanList;
    }

    public BigDecimal getPaymentFee() {
        return paymentFee;
    }

    public void setPaymentFee(BigDecimal paymentFee) {
        this.paymentFee = paymentFee;
    }

    public List<AdvanceFeePlan> getAdvanceFeePlanList() {
        return advanceFeePlanList;
    }

    public void setAdvanceFeePlanList(List<AdvanceFeePlan> advanceFeePlanList) {
        this.advanceFeePlanList = advanceFeePlanList;
    }

    public Integer getMaxPeriod() {
        return maxPeriod;
    }

    public void setMaxPeriod(Integer maxPeriod) {
        this.maxPeriod = maxPeriod;
    }

    public SubjectEnum getMaxPeriodSubject() {
        return maxPeriodSubject;
    }

    public void setMaxPeriodSubject(SubjectEnum maxPeriodSubject) {
        this.maxPeriodSubject = maxPeriodSubject;
    }

    public BigDecimal getTotalInt() {
        return totalInt;
    }

    public void setTotalInt(BigDecimal totalInt) {
        this.totalInt = totalInt;
    }

    public BigDecimal getTotalPfee() {
        return totalPfee;
    }

    public void setTotalPfee(BigDecimal totalPfee) {
        this.totalPfee = totalPfee;
    }

    public BigDecimal getTotalSfee() {
        return totalSfee;
    }

    public void setTotalSfee(BigDecimal totalSfee) {
        this.totalSfee = totalSfee;
    }

    public List<PeriodFeePlan> getPeriodPlanList() {
        return periodPlanList;
    }

    public void setPeriodPlanList(List<PeriodFeePlan> periodPlanList) {
        this.periodPlanList = periodPlanList;
    }

    public String getCycleType() {
        return cycleType;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    public String getCalculateRate() {
        return calculateRate;
    }

    public void setCalculateRate(String calculateRate) {
        this.calculateRate = calculateRate;
    }

    public String getCalculationFormula() {
        return calculationFormula;
    }

    public void setCalculationFormula(String calculationFormula) {
        this.calculationFormula = calculationFormula;
    }
}
