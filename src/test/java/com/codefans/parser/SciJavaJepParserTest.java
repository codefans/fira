package com.codefans.parser;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: codefans
 * @Date: 2019-04-08 23:36
 */

public class SciJavaJepParserTest {

    @Test
    public void test() {

        SciJavaJepParser sciJavaJepParser = new SciJavaJepParser();
        Map<String,String> map = new HashMap<String,String>();
        map.put("y", "80000000");
        map.put("irr", "0.062");
        map.put("c", "0.46");
        map.put("nc", "6");
        String formula = "Round(Round(y*irr*nc/360)-Round(y*irr*(nc-1)/360))";
        System.out.println(sciJavaJepParser.calculationEntrance(formula,map));

    }
}
