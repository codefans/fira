package com.codefans.enums;

/**
 * @Author: codefans
 * @Date: 2019-04-13 23:06
 */

public enum SubjectEnum {

    PRI("PRI", "本金"),
    //执行利率
    INT("INT", "利息"),
    //逾期利率
    PIN("PIN", "罚息"),

    SFEE("SFEE", "服务费"),
    PFEE("PFEE", "手续费"),
    RECPFEE("RECPFEE", "提前还款手续费"),
    ROCPFEE("ROCPFEE", "展期费"),

    CIN("CIN", "复息"),
    BOC("BOC", "违约金"),
    FDP("FDP", "滞纳金"),
    PSFEE("PSFEE", "罚服务费"),
    ASFEE("ASFEE", "复服务费"),

    //挪用费
    NYFEE("NYFEE","挪用费"),
    //提前还款费
    TQHKFEE("TQHKFEE","提前还款费"),
    //展期费
    ZQFEE("ZQFEE","展期费");

    private String name;
    private String desc;

    private SubjectEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
