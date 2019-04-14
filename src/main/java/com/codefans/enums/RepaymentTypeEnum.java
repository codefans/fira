package com.codefans.enums;

/**
 * @Author: codefans
 * @Date: 2019-04-13 19:57
 * 还款方式
 */
public enum RepaymentTypeEnum {

    ODDS("ODDS", "一次性还本付息"),
    ODDS_F("ODDS_F", "一次性还本付息(拆分)"),
    MPDD("MPDD", "按月付息到期还本"),
    SPDD("SPDD", "按季付息到期还本"),
    ECI("ECI", "等本等息"),
    BARA("BARA", "随借随还"),	//borrow anytime return anytime
    AC("AC", "等额本金"),		//average capital
    ACI("ACI", "等额本息");

    public String type;
    public String desc;

    RepaymentTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
