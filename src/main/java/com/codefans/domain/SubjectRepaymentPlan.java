package com.codefans.domain;

import java.math.BigDecimal;

/**
 * @Author: codefans
 * @Date: 2019-04-13 23:49
 */

public class SubjectRepaymentPlan extends DateInterval {

    /**
     * 费用科目
     */
    private String  feeType;
    /**
     * 费用名称
     */
    private String  feeName;
    /**
     * 费用计算公式
     */
    private String  feeFormula;
    /**
     * 费用金额
     */
    private BigDecimal feeAmount;

    /**
     * 当期应还本金--仅本金测算使用
     */
    private BigDecimal shouldPrincipal;
    /**
     * 本金计算公式--仅本金测算使用
     */
    private String principalFormula;
    /**
     * 当期实际剩余本金--仅本金测算使用
     */
    private BigDecimal remainPrincipal;
    /**
     * 当期测算本金--仅本金测算使用
     */
    private BigDecimal calculatePrincipal;
    /**
     * 当前周期--仅本金测算使用
     */
    private String days;

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getFeeFormula() {
        return feeFormula;
    }

    public void setFeeFormula(String feeFormula) {
        this.feeFormula = feeFormula;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public BigDecimal getShouldPrincipal() {
        return shouldPrincipal;
    }

    public void setShouldPrincipal(BigDecimal shouldPrincipal) {
        this.shouldPrincipal = shouldPrincipal;
    }

    public String getPrincipalFormula() {
        return principalFormula;
    }

    public void setPrincipalFormula(String principalFormula) {
        this.principalFormula = principalFormula;
    }

    public BigDecimal getRemainPrincipal() {
        return remainPrincipal;
    }

    public void setRemainPrincipal(BigDecimal remainPrincipal) {
        this.remainPrincipal = remainPrincipal;
    }

    public BigDecimal getCalculatePrincipal() {
        return calculatePrincipal;
    }

    public void setCalculatePrincipal(BigDecimal calculatePrincipal) {
        this.calculatePrincipal = calculatePrincipal;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
