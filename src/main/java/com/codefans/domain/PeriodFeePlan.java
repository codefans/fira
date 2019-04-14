package com.codefans.domain;

import java.util.Date;
import java.util.List;

/**
 * @Author: codefans
 * @Date: 2019-04-14 0:34
 * 分期费用计划
 */

public class PeriodFeePlan {

    /**
     * 阶段
     */
    private Integer phase;
    /**
     * 分期
     */
    private Integer period;
    /**
     * 当前计划开始日期
     */
    private Date startDate;
    /**
     * 当前计划结束日期
     */
    private Date endDate;
    /**
     * 当期还款日
     */
    private Date repaymentDate;
    /**
     * 当期应还本金
     */
    private Long principal;
    /**
     * 本金计算公式
     */
    private String principalFormula;
    /**
     * 当期应还利息
     */
    private Long interest;
    /**
     * 利息计算公式
     */
    private String interestFormula;
    /**
     * 当期应还费用总和
     */
    private Long peroidFeeAmount;
    /**
     * 当期应还费用明细
     */
    private List<PeriodFee> periodFeeList;
    /**
     * 当期应还金额
     */
    private Long periodAmount;
    /**
     * 状态
     */
    private String status;
    /**
     * 剩余本金
     */
    private Long remainPrincipal;
    /**
     * 下次还款日
     */
    private Date nextRepaymentDate;
    /**
     * 下次还本日
     */
    private Date nextPrincipalDate;
    /**
     * 下次还息日
     */
    private Date nextInterestDate;
    /**
     * 当前还本日
     */
    private Date principalDate;
    /**
     * 当前还息日
     */
    private Date interestDate;
    /**
     * 还本周期天数
     */
    private Integer debtCycleDays;
    /**
     * 计算本金
     */
    private Long calculatePrincipal;

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Long getPrincipal() {
        return principal;
    }

    public void setPrincipal(Long principal) {
        this.principal = principal;
    }

    public String getPrincipalFormula() {
        return principalFormula;
    }

    public void setPrincipalFormula(String principalFormula) {
        this.principalFormula = principalFormula;
    }

    public Long getInterest() {
        return interest;
    }

    public void setInterest(Long interest) {
        this.interest = interest;
    }

    public String getInterestFormula() {
        return interestFormula;
    }

    public void setInterestFormula(String interestFormula) {
        this.interestFormula = interestFormula;
    }

    public Long getPeroidFeeAmount() {
        return peroidFeeAmount;
    }

    public void setPeroidFeeAmount(Long peroidFeeAmount) {
        this.peroidFeeAmount = peroidFeeAmount;
    }

    public List<PeriodFee> getPeriodFeeList() {
        return periodFeeList;
    }

    public void setPeriodFeeList(List<PeriodFee> periodFeeList) {
        this.periodFeeList = periodFeeList;
    }

    public Long getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(Long periodAmount) {
        this.periodAmount = periodAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRemainPrincipal() {
        return remainPrincipal;
    }

    public void setRemainPrincipal(Long remainPrincipal) {
        this.remainPrincipal = remainPrincipal;
    }

    public Date getNextRepaymentDate() {
        return nextRepaymentDate;
    }

    public void setNextRepaymentDate(Date nextRepaymentDate) {
        this.nextRepaymentDate = nextRepaymentDate;
    }

    public Date getNextPrincipalDate() {
        return nextPrincipalDate;
    }

    public void setNextPrincipalDate(Date nextPrincipalDate) {
        this.nextPrincipalDate = nextPrincipalDate;
    }

    public Date getNextInterestDate() {
        return nextInterestDate;
    }

    public void setNextInterestDate(Date nextInterestDate) {
        this.nextInterestDate = nextInterestDate;
    }

    public Date getPrincipalDate() {
        return principalDate;
    }

    public void setPrincipalDate(Date principalDate) {
        this.principalDate = principalDate;
    }

    public Date getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(Date interestDate) {
        this.interestDate = interestDate;
    }

    public Integer getDebtCycleDays() {
        return debtCycleDays;
    }

    public void setDebtCycleDays(Integer debtCycleDays) {
        this.debtCycleDays = debtCycleDays;
    }

    public Long getCalculatePrincipal() {
        return calculatePrincipal;
    }

    public void setCalculatePrincipal(Long calculatePrincipal) {
        this.calculatePrincipal = calculatePrincipal;
    }
}
