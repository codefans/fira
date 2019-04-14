package com.codefans.domain;

import java.util.Date;

/**
 * @Author: codefans
 * @Date: 2019-04-14 0:41
 */

public class PeriodFee {

    /**
     * 费用科目
     */
    private String feeType;
    /**
     * 费用名称
     */
    private String feeName;
    /**
     * 费用计算公式
     */
    private String feeFormula;
    /**
     * 费用金额
     */
    private Long feeAmount;
    /**
     * 下次还款日
     */
    private Date nextFeeDate;

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

    public Long getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Long feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Date getNextFeeDate() {
        return nextFeeDate;
    }

    public void setNextFeeDate(Date nextFeeDate) {
        this.nextFeeDate = nextFeeDate;
    }
}
