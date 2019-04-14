package com.codefans.enums;

/**
 * @Author: codefans
 * @Date: 2019-04-13 23:17
 * 收取周期
 */

public enum CycleTypeEnum {

    FREE("FREE", "不收"),
    FORMER("FORMER", "趸交（先收）"),
    FORMER_L("FORMER_L", "预收+最后"),
    LAST("LAST", "最后还款日一次性交付"),
    DAY("DAY", "按日"),
    MONTH("MONTH", "按月"),
    SEASON("SEASON", "按季"),
    YEAR("YEAR", "按年");

    private String name;
    private String desc;

    private CycleTypeEnum(String name, String desc) {
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
