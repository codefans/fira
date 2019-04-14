package com.codefans.calc.engine;

import com.codefans.domain.SubjectRepaymentPlan;
import com.codefans.domain.param.CalculateParam;

import java.util.List;

/**
 * @Author: codefans
 * @Date: 2019-04-13 23:15
 * 计算引擎
 */

public interface CalculateEngine {

    /**
     * 生成(某一科目)还款计划
     * @param calculateParam
     * @return
     */
    List<SubjectRepaymentPlan> execute(CalculateParam calculateParam);

}
