package com.jiashn.springbootproject.useUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/3/17 13:59
 **/
public class UseString {

    public static void main(String[] args) {
        String equation = "({83d0b5046cdb3a206968d4eb156e3a1f}+{83d0b5046cdb3a206968d4eb156e3a1f})+{83d0b5046cdb3a206968d4eb156e3a1f}*{0d8fda0b68eaac1972e4c26edf7074f2}+{0d8fda0b68eaac1972e4c26edf7074f2}";
        String replaceStr = equation.replace("{", "&").replace("}", "&");
        System.out.println("替换后数据："+replaceStr);
        List<String> equations = Stream.of(StringUtils.split(replaceStr, "&")).collect(Collectors.toList());
        List<String> list = Arrays.asList(StringUtils.split(replaceStr, "&"));
        equations.remove(")+");
        list = equations;
        Map<String,Double> resMap = new HashMap<>(2);
        resMap.put("83d0b5046cdb3a206968d4eb156e3a1f",20d);
        resMap.put("0d8fda0b68eaac1972e4c26edf7074f2",10d);
        StringBuilder equationVal = new StringBuilder();
        for (String e : equations) {
            if (!Objects.equals("&",e)){
                if (resMap.containsKey(e)){
                    e = String.valueOf(resMap.get(e));
                }
                equationVal.append(e);
            }
        }
        System.out.println("公式：" + equationVal.toString());
        List<String> ids = Stream.of(StringUtils.split(replaceStr, "&"))
                .filter(rep -> rep.length() > 1)
                .collect(Collectors.toList());
        for (String id : ids) {
            System.out.println("获取到的id：" + id);
        }
    }
}
