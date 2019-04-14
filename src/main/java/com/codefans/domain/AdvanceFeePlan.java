package com.codefans.domain;

import java.io.Serializable;

/**
 * @Author: codefans
 * @Date: 2019-04-14 0:31
 * 预收费用计划
 */

public class AdvanceFeePlan implements Serializable {

    /**
     * 费用类型
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
     * 费用
     */
    private Long feeAmount;

    /**
     * 状态
     */
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
