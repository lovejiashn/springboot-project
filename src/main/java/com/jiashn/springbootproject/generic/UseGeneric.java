package com.jiashn.springbootproject.generic;

import java.util.*;

/**
 * @author: jiangjs
 * @description: 泛型：即java中可以多类型进行匹配的，一般使用T,R等进行表示
 *               类型擦除：java在编译时会根据传入的类型进行编译，编译完成后会简化成公用的类。例如：
 *                    List<String> resList = new ArrayList<>();List<Integer> intList = new ArrayList<>();
 *                    在编译完成后都会被简化成ArrayList。
 *               类型擦除不同情况：1、无限制类型擦除：即编译后泛型被替换成Object
 *                              2、有限制类型擦除：即有上下界类型，会被替换成指定类型。例如：<? extend Integer>被指定成Integer
 *               类型擦除的作用：避免创建过多类，造成运行时过渡消耗
 * @date: 2023/3/1 9:57
 **/
public class UseGeneric {

    public static void main(String[] args) {
        List<String> strList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        System.out.println("strList Class >>>>>>>:"+strList.getClass());
        System.out.println("intList Class >>>>>>>:"+intList.getClass());
        System.out.println(strList.getClass() == intList.getClass());

        System.out.println("strList 类型："+strList.getClass().getTypeParameters());
        System.out.println("intList 类型："+intList.getClass().getTypeParameters());

        Map<String,Integer> map = new HashMap<>(2);
        System.out.println("Map 类型："+Arrays.asList(map.getClass().getTypeParameters()));
    }
}
