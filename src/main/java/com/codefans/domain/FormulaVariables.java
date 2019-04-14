package com.codefans.domain;

/**
 * @Author: codefans
 * @Date: 2019-04-14 0:17
 */

public class FormulaVariables {

    //正常应收和计提共有变量
    //周期测算本金
    private String y;
    //年利率/收息比例/固定值
    private String irr;
    //本次周期已发生天数（按本）
    private String nc;
    //本次周期天数（按本）
    private String dd;
    //年费率/收费比例/固定值
    private String firr;
    //当前剩余本金
    private String rp;
    //分期数
    private String x;

    //逾期应收确认部分涉及到的变量
    //逾期罚服务费利率/比例/固定值
    private String oveirr;
    //欠本
    private String ovey;
    //欠息
    private String over;
    //欠费
    private String ovef;
    //欠罚服务费
    private String ovepf;
    //欠罚息
    private String ovepr;
    //欠复息
    private String ovear;
    //欠复服务费
    private String oveaf;

    public String getRp() {
        return rp;
    }
    public void setRp(String rp) {
        this.rp = rp;
    }
    public String getOvear() {
        return ovear;
    }
    public void setOvear(String ovear) {
        this.ovear = ovear;
    }
    public String getOveaf() {
        return oveaf;
    }
    public void setOveaf(String oveaf) {
        this.oveaf = oveaf;
    }
    public String getY() {
        return y;
    }
    public void setY(String y) {
        this.y = y;
    }
    public String getIrr() {
        return irr;
    }
    public void setIrr(String irr) {
        this.irr = irr;
    }
    public String getNc() {
        return nc;
    }
    public void setNc(String nc) {
        this.nc = nc;
    }
    public String getFirr() {
        return firr;
    }
    public void setFirr(String firr) {
        this.firr = firr;
    }
    public String getOveirr() {
        return oveirr;
    }
    public void setOveirr(String oveirr) {
        this.oveirr = oveirr;
    }
    public String getOvey() {
        return ovey;
    }
    public void setOvey(String ovey) {
        this.ovey = ovey;
    }
    public String getOver() {
        return over;
    }
    public void setOver(String over) {
        this.over = over;
    }
    public String getOvef() {
        return ovef;
    }
    public void setOvef(String ovef) {
        this.ovef = ovef;
    }
    public String getOvepf() {
        return ovepf;
    }
    public void setOvepf(String ovepf) {
        this.ovepf = ovepf;
    }
    public String getOvepr() {
        return ovepr;
    }
    public void setOvepr(String ovepr) {
        this.ovepr = ovepr;
    }
    public String getDd() {
        return dd;
    }
    public void setDd(String dd) {
        this.dd = dd;
    }
    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }

}
