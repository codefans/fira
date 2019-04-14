package com.codefans.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: codefans
 * @Date: 2019-04-14 0:00
 * 日期间隔
 */

public class DateInterval implements Serializable {

    /**
     * 期数
     */
    private int period;

    /**
     * 还款计划开始日期
     */
    private Date startDate;
    /**
     * 还款计划结束日期
     */
    private Date endDate;
    /**
     * 还款日
     */
    private Date repaymentDate;
    /**
     * 下次还款日
     */
    private Date nextRepayDate;

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
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

    public Date getNextRepayDate() {
        return nextRepayDate;
    }

    public void setNextRepayDate(Date nextRepayDate) {
        this.nextRepayDate = nextRepayDate;
    }

    /**
     * 获取本次周期已发生天数
     * @return
     */
    public String getDays() {
        long timeDiff = getEndDate().getTime() - getStartDate().getTime();
        return String.valueOf(timeDiff/(24*60*60*1000) + 1L);
    }

    public long getDaysLong() {
        long timeDiff = getEndDate().getTime() - getStartDate().getTime();
        return timeDiff/(24*60*60*1000) + 1L;
    }

}
