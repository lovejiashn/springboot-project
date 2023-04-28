package com.jiashn.springbootproject.useUtil;

import com.jiashn.springbootproject.useUtil.domain.Expert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: jiangjs
 * @description: 使用stream 专家
 * @date: 2022/9/13 11:18
 **/
public class UseStream {
    public static void main(String[] args) {
        List<Expert> experts = Arrays.asList(
                new Expert("jiashn","男",20),
                new Expert("张三-zhangsan","女",21),
                new Expert("王五-wangwu","女",26),
                new Expert("李四-lisi","男",24));
        Stream<Expert> expertStream = Stream.of(new Expert("赵一-zhaoyi", "男", 20),
                new Expert("王定六-wangdingliu", "男", 21));
        //过滤年龄小于25的人
        List<Expert> ageJia = experts.stream().filter(jia -> jia.getAge() <= 25).collect(Collectors.toList());
        System.out.println("年龄小于25岁的人的信息：" + ageJia);
        //获取所以成员的名字信息
        String names = experts.stream().map(Expert::getName).collect(Collectors.joining(","));
        System.out.println("所有人员的名称：" + names);
        //拆分名字
        List<String> splitName = experts.stream().flatMap(exp -> Stream.of(exp.getName().split("-")))
                .collect(Collectors.toList());
        System.out.println("拆分名字结果：" + splitName);
        //年龄排序，正序或倒序
        List<Expert> sortInfo = experts.stream().sorted(Comparator.comparing(Expert::getAge))
                .collect(Collectors.toList());
        List<Expert> reversedInfo = experts.stream().sorted(Comparator.comparing(Expert::getAge).reversed())
                .collect(Collectors.toList());
        System.out.println("人员年龄排序（升序）：" + sortInfo+"；人员年龄排序（降序）："+reversedInfo);
        //截取前面两条记录信息
        List<Expert> twoInfos = experts.stream().skip(0).limit(2).collect(Collectors.toList());
        System.out.println("截取前两条记录："+twoInfos);
        //过滤获取第一个元素的姓名
        String name = experts.stream().filter(jia -> Objects.equals(jia.getAge(), 26))
                .findFirst().orElseGet(Expert::new).getName();
        System.out.println("获取符合条件查询的名称："+name);
        //根据性别进行分组
        Map<String, List<Expert>> genderMap = experts.stream().collect(Collectors.groupingBy(Expert::getGender));
        System.out.println("性别分组信息："+genderMap);
        //汇总
        DoubleSummaryStatistics statistics = experts.stream().collect(Collectors.summarizingDouble(Expert::getAge));
        System.out.println("年龄最大值："+statistics.getMax());
        System.out.println("年龄最小值："+statistics.getMin());
        System.out.println("年龄平均值："+statistics.getAverage());
        System.out.println("年龄总和值："+statistics.getSum());
        System.out.println("总人数："+statistics.getCount());
        //分区
        Map<Boolean, List<Expert>> collect = experts.stream().collect(Collectors.partitioningBy(jia -> jia.getAge() > 21));
        List<Expert> gtExpert= collect.get(Boolean.TRUE);
        List<Expert> ltExpert= collect.get(Boolean.FALSE);
        System.out.println("年龄大于21岁的数据："+gtExpert);
        System.out.println("年龄小于等于21岁的数据："+ltExpert);

    }
}