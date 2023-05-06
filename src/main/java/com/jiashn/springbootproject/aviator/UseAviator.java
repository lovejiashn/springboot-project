package com.jiashn.springbootproject.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 使用谷歌aviator进行表达式计算
 * @date: 2023/3/27 10:06
 **/
public class UseAviator {

    public static void main(String[] args) {
        String epx = "a * b + c";
        Map<String,Object> paraMap = new HashMap<>(3);
        paraMap.put("a",10.1);
        paraMap.put("b",10);
        paraMap.put("c",2);
        String epx2 = "(a-b)*c";
        Double result = AviatorUtil.<Double>computeExp(paraMap, epx);
        Double maxVal = AviatorUtil.<Double>computeExp(paraMap, "max(a,b,c)");
       // Double result2 = AviatorUtil.<Double>computeExp(paraMap, epx2);
        //System.out.println("计算结果："+result);
        System.out.println("计算结果："+result);
        System.out.println("最大值："+maxVal);
    }
}
