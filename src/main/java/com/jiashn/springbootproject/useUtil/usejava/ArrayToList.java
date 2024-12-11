package com.jiashn.springbootproject.useUtil.usejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jiangjs
 * @description: 数组转list 或 list转数组
 * @date: 2024/12/10 10:19
 **/
public class ArrayToList {
    public static void main(String[] args) {
        //使用Collections进行元素添加,能正常添加、删除数据
        String[] ary = new String[]{"1","2"};
        List<String> list = new ArrayList<>(ary.length);
        Collections.addAll(list,ary);
        list.add("hello");
        System.out.println(list);

        //通过new ArrayList(Arrays.asList())直接进行转换，可进行数据添加、删除
        List<String> strings = new ArrayList<>(Arrays.asList(ary));
        strings.add("moto");
        System.out.println(strings);

        //使用Arrays.stream(ary).collect(Collectors.toList())
        List<String> collect = Arrays.stream(ary).collect(Collectors.toList());
        collect.add("hello");
        System.out.println(collect);

        //list转数组, toArray可以创建一个转换类型空数组，实现转换
        String[] array = collect.toArray(new String[]{});
        System.out.println("数组输出："+Arrays.toString(array));

        //Arrays.asList()转list无法进行数据添加、删除
        List<String> out = Arrays.asList(ary);
        out.add("hello");
        System.out.println(out);



    }
}
