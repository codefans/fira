package com.codefans.calc.factory;

import com.codefans.calc.engine.*;
import com.codefans.enums.SubjectEnum;

/**
 * @Author: codefans
 * @Date: 2019-04-13 23:14
 * 计算引擎工厂
 */

public class CalculateEngineFactory {

    /**
     * 获取科目计算引擎
     * @param subject
     * @return
     */
    public CalculateEngine getEngine(SubjectEnum subject){
        CalculateEngine calculateEngine = null;
        if("PRI".equals(subject.getName())) {
            calculateEngine = new PRICalculateEngine();
        } else if("INT".equals(subject.getName())) {
//            calculateEngine = new INTCalculateEngine();
        } else if("SFEE".equals(subject.getName())) {
//            calculateEngine = new SFEECalculateEngine();
        } else if("PFEE".equals(subject.getName())) {
//            calculateEngine = new PFEECalculateEngine();
        } else if("RECPFEE".equals(subject.getName())) {
//            calculateEngine = new RECPFEECalculateEngine();
        } else if("".equals(subject.getName())) {

        } else if("".equals(subject.getName())) {

        } else if("".equals(subject.getName())) {

        } else {
            throw new RuntimeException("找不到相应的计算引擎");
        }
        return calculateEngine;
    }


}
