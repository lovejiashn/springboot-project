package com.jiashn.springbootproject.useUtil;

import com.jiashn.springbootproject.useUtil.domain.Jiashn;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: jiangjs
 * @description: 使用stream
 * @date: 2022/9/13 11:18
 **/
public class UseStream {
    public static void main(String[] args) {
        List<Jiashn> jias = Arrays.asList(
                new Jiashn("jiashn","男",20),
                new Jiashn("张三","女",21),
                new Jiashn("王五","女",26),
                new Jiashn("李四","男",24));
        //过滤年龄小于25的人
        List<Jiashn> ageJia = jias.stream().filter(jia -> jia.getAge() <= 25).collect(Collectors.toList());
        System.out.println("年龄小于25岁的人的信息：" + ageJia);
        //获取所以成员的名字信息
        String names = jias.stream().map(Jiashn::getName).collect(Collectors.joining(","));
        System.out.println("所有人员的名称：" + names);
        //年龄排序，正序或倒序
        List<Jiashn> sortInfo = jias.stream().sorted(Comparator.comparing(Jiashn::getAge))
                .collect(Collectors.toList());
        List<Jiashn> reversedInfo = jias.stream().sorted(Comparator.comparing(Jiashn::getAge).reversed())
                .collect(Collectors.toList());
        System.out.println("人员年龄排序（升序）：" + sortInfo+"；人员年龄排序（降序）："+reversedInfo);
        //截取前面两条记录信息
        List<Jiashn> twoInfos = jias.stream().skip(0).limit(2).collect(Collectors.toList());
        System.out.println("截取前两条记录："+twoInfos);
        //过滤获取第一个元素的姓名
        String name = jias.stream().filter(jia -> Objects.equals(jia.getAge(), 26))
                .findFirst().orElseGet(Jiashn::new).getName();
        System.out.println("获取符合条件查询的名称："+name);
        //根据性别进行分组
        Map<String, List<Jiashn>> genderMap = jias.stream().collect(Collectors.groupingBy(Jiashn::getGender));
        System.out.println("性别分组信息："+genderMap);
        //汇总
        DoubleSummaryStatistics statistics = jias.stream().collect(Collectors.summarizingDouble(Jiashn::getAge));
        System.out.println("年龄最大值："+statistics.getMax());
        System.out.println("年龄最小值："+statistics.getMin());
        System.out.println("年龄平均值："+statistics.getAverage());
        System.out.println("年龄总和值："+statistics.getSum());
        System.out.println("总人数："+statistics.getCount());
        //分区
        Map<Boolean, List<Jiashn>> collect = jias.stream().collect(Collectors.partitioningBy(jia -> jia.getAge() > 21));
        List<Jiashn> gtJiashn= collect.get(Boolean.TRUE);
        List<Jiashn> ltJiashn= collect.get(Boolean.FALSE);
        System.out.println("年龄大于21岁的数据："+gtJiashn);
        System.out.println("年龄小于等于21岁的数据："+ltJiashn);

    }
}