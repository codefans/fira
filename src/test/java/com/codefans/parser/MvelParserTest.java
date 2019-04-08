package com.codefans.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;

import java.util.*;

/**
 * @Author: codefans
 * @Date: 2019-04-08 23:17
 */

public class MvelParserTest {

    private MvelParser mvelParser;

    @Before
    public void before() {
        mvelParser = new MvelParser();
    }

    @Test
    public void testCase01() {

        List<String> dataList = new ArrayList<String>();
        dataList.add("aaaaa");
        dataList.add("bbbbb");
        dataList.add("ccccc");

        String expressionStr = "(dataList.size() >= 3 && !dataList.contains(keyStr))";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dataList", dataList);
        paramMap.put("keyStr", "aaaaa");
        Assert.assertFalse(this.evaluate(paramMap, expressionStr));

        paramMap = new HashMap<String, Object>();
        paramMap.put("dataList", dataList);
        paramMap.put("keyStr", "ddddd");
        Assert.assertTrue(this.evaluate(paramMap, expressionStr));
    }

    @Test
    public void testCase02() {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("payAmount", "200");
        paramMap.put("repayAmount", "400");
        String expressionStr = "(payAmount > 500-repayAmount)";
        //print true
        System.out.println(this.evaluate(paramMap, expressionStr));

        paramMap = new HashMap<String, Object>();
        paramMap.put("payAmount", "200");
        paramMap.put("repayAmount", "100");
        expressionStr = "(payAmount > 500-repayAmount)";
        //print false
        System.out.println(this.evaluate(paramMap, expressionStr));

    }

    @Test
    public void testCase03() {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("keyStr", "");
        String expressionStr = "(keyStr == empty)";
        Assert.assertTrue(this.evaluate(paramMap, expressionStr));

        paramMap = new HashMap<String, Object>();
        paramMap.put("keyStr", " ");
        expressionStr = "(keyStr == empty)";
        Assert.assertTrue(this.evaluate(paramMap, expressionStr));

        paramMap = new HashMap<String, Object>();
        paramMap.put("keyStr", null);
        expressionStr = "(keyStr == empty)";
        Assert.assertTrue(this.evaluate(paramMap, expressionStr));

        paramMap = new HashMap<String, Object>();
        paramMap.put("keyStr", "abc");
        expressionStr = "(keyStr == empty)";
        Assert.assertFalse(this.evaluate(paramMap, expressionStr));

    }

    @Test
    public void testCase04() {

        String expressionStr = "(keyStr >= 1)";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("keyStr", 2);
        Assert.assertTrue(this.evaluate(paramMap, expressionStr));

        paramMap = new HashMap<String, Object>();
        paramMap.put("keyStr", 0);
        Assert.assertFalse(this.evaluate(paramMap, expressionStr));

    }

    @Test
    public void testCase05() {

        String expressionStr = "( ( com.codefans.parser.RuleTools.getTime(createDate) >= 0 && ( age >= 3 ) )";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("createDate", new Date());
        paramMap.put("age", "4");
        Assert.assertTrue(this.evaluate(paramMap, expressionStr));

        paramMap = new HashMap<String, Object>();
        paramMap.put("createDate", new Date());
        paramMap.put("age", "2");
        Assert.assertFalse(this.evaluate(paramMap, expressionStr));

    }

    @Test
    public void testCase06() {

        String expressionStr = "( dataList.size() >= 5 && type == '6' && !dataList.contains(keyStr) )";

        List<String> dataList = new ArrayList<String>();
        dataList.add("aaaaa");
        dataList.add("bbbbb");
        dataList.add("ccccc");
        dataList.add("ddddd");
        dataList.add("eeeee");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dataList", dataList);
        paramMap.put("type", "6");
        paramMap.put("keyStr", "fffff");
        System.out.println(this.evaluate(paramMap, expressionStr));
        Assert.assertTrue(this.evaluate(paramMap, expressionStr));

        paramMap = new HashMap<String, Object>();
        paramMap.put("dataList", dataList);
        paramMap.put("type", 6);
        paramMap.put("keyStr", "eeeee");
        System.out.println(this.evaluate(paramMap, expressionStr));
        Assert.assertFalse(this.evaluate(paramMap, expressionStr));


    }

    public boolean evaluate(Map<String, Object> paramMap, String expressionStr) {

        CompiledExpression compiledExpression = null;
        try{
            ExpressionCompiler compiler = new ExpressionCompiler(expressionStr);
            compiledExpression = compiler.compile();
        }catch(Exception e){
            e.printStackTrace();
        }
        return mvelParser.simpleEvaluate(paramMap, compiledExpression);
    }

}
