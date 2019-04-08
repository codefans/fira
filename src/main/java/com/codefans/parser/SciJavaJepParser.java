package com.codefans.parser;

import org.lsmp.djep.xjep.TreeUtils;
import org.lsmp.djep.xjep.XJep;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @Author: codefans
 * @Date: 2019-04-08 23:04
 */

public class SciJavaJepParser {

    /**
     *
     * @param expressionStr
     * @param paramMap
     * @return
     */
    public static BigDecimal validateExpressionStr(String expressionStr, Map<String, String> paramMap ) {
        BigDecimal result = BigDecimal.ZERO;
        XJep xJep = initialization();
        Node expNode = null;
        try {

            expNode = xJep.parse(expressionStr);
            Node expParserNode = xJep.preprocess(expNode);
            TreeUtils treeUtils = xJep.getTreeUtils();

            Collection<Node> nodeCollection = genNodesCol(expParserNode,treeUtils);
            if (containsAll(nodeCollection,xJep,treeUtils,paramMap)) {
                String reStr = calculate(expressionStr, paramMap, xJep);
                if(reStr!=null&&reStr.length()>0){
                    result = new BigDecimal(reStr).setScale(2, RoundingMode.HALF_UP);
                }
                return result;
            } else {
                String message ="parameters and formula to define variables do not meet";
                throw new RuntimeException(message);
            }
        } catch (ParseException e) {
            String message = "failed to parse expression : "+expressionStr+" for incorrect formation";
            System.out.println(message);
            throw new RuntimeException(message, e);
        }
    }


    public static List<Node> genNodesBeneath(TreeUtils treeUtils, Node root){
        List<Node> destList = new LinkedList<>();
        Node[] children = treeUtils.getChildrenAsArray(root);
        for(Node child : children){
            if(treeUtils.isVariable(child)){
                destList.add(child);
            }
            destList.addAll(genNodesBeneath(treeUtils,child));
        }
        return destList;
    }


    /**
     * 参数校验,进行变量初始化
     * @param nodes
     * @param xJep
     * @param treeUtils
     * @param paramMap
     * @return
     */
    public static boolean containsAll(Collection<Node> nodes, XJep xJep ,TreeUtils treeUtils, Map<String, String> paramMap ) {
        Set<String> constantsKeysSet = paramMap.keySet();
        Set<String> nodeNames = new HashSet<>();
        for(Node node : nodes){
            if(treeUtils.isVariable(node)){
                nodeNames.add(treeUtils.getName(node));
                String key =treeUtils.getName(node);
                xJep.setVarValue(key, getBigDecimal(paramMap.get(treeUtils.getName(node))));
            }
            if(!constantsKeysSet.contains(treeUtils.getName(node))){
                return false;
            }
        }
        return true;
    }

    public static BigDecimal getBigDecimal(String decimalStr) {
        return new BigDecimal(decimalStr);
    }

    /**
     * 初始化公式引擎
     */
    public static XJep initialization(){
        /* initilisation */
        XJep xJep = new XJep();
        xJep.addStandardConstants();
        xJep.addStandardFunctions();
        xJep.addComplex();
        xJep.setAllowUndeclared(true);
        xJep.setAllowAssignment(true);
        xJep.setImplicitMul(false);
        return xJep;
    }
    /**
     * 计算引擎
     */
    public static String calculate(String expression, Map<String, String> paramMap, XJep xJep ) {
        //result
        String result = "0.00";
        String message = null;
        try{
            Node expNode = xJep.parse(expression);
            Node expParserNode = xJep.preprocess(expNode);

            result = xJep.evaluate(expParserNode).toString();
            System.out.println("result of expression:"+expression+" is "+result);
        }
        catch(ParseException e){
            message = "failed to parse expression ["+expression+"]";
            System.out.println(message+e);
            throw new RuntimeException(message, e);
        }
        catch(Exception e){
            System.out.println("Error with evaluation");
            throw new RuntimeException("Error with evaluation", e);
        }
        return result;
    }



    private static Collection<Node> genNodesCol(Node expParserNode,TreeUtils treeUtils) {
        Collection<Node> col = new LinkedList<>();

        col = genNodesBeneath(treeUtils, expParserNode);
        if(treeUtils.isVariable(expParserNode)){
            col.add(expParserNode);
        }

        return col;
    }
    public static BigDecimal calculationEntrance(String formula,Map<String, String> paramMap) {
        int size = "Round(".length();
        int indexOf= formula.indexOf("Round(");
        String other = "";
        int zheng = 1;
        int fan = 0;
        if(indexOf<0){
            return validateExpressionStr(formula, paramMap);
        }else {
            for(int i = indexOf+size;i< formula.length();i++){
                String fuhao  = formula.substring(i, i+1);
                if(fuhao.equals("(")){
                    zheng = zheng +1;
                }
                if(fuhao.equals(")")){
                    fan = fan+1;

                }
                if(zheng==fan){
                    String jisuanchuaner  = formula.substring(indexOf+size, i);
                    if(jisuanchuaner.indexOf("Round(")<0){
                        String tihuan  = formula.substring(indexOf, i+1);
                        other = formula.replace(tihuan, validateExpressionStr(jisuanchuaner, paramMap).toString());
                        break;
                    }else{
                        other=formula.substring(indexOf+size, i);
                        break;
                    }

                }
            }
            return calculationEntrance(other,paramMap);
        }
    }



}
