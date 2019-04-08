package com.codefans.parser;

import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;

import java.util.Map;

/**
 * @Author: codefans
 * @Date: 2019-04-08 23:05
 */

public class MvelParser {

    public boolean simpleEvaluate(Map<String, Object> mvelMap, CompiledExpression expression){
        return MVEL.executeExpression(expression, mvelMap, Boolean.class);
    }

}
