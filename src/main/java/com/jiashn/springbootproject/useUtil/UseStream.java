package com.jiashn.springbootproject.useUtil;

import com.jiashn.springbootproject.useUtil.domain.Expert;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
                new Expert("李四-lisi","男",24),
                new Expert("李四-lisi","男",24)
        );
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
        //去重
        List<Expert> disExperts = experts.stream().distinct()
                .collect(Collectors.toList());
        System.out.println("去重：" + disExperts);
        //截取前面两条记录信息
        List<Expert> twoInfos = experts.stream().skip(2).limit(2).collect(Collectors.toList());
        System.out.println("截取记录："+twoInfos);
        //allMatch
        boolean allMatch = experts.stream().allMatch(expert -> Objects.equals(expert.getGender(), "男"));
        System.out.println("是否性别都为男："+allMatch);
        //anyMatch
        boolean ageMatch = experts.stream().anyMatch(expert -> Objects.equals(expert.getGender(), "男"));
        System.out.println("是否存在性别为男："+ageMatch);
        //noneMatch
        boolean noneMatch = experts.stream().noneMatch(expert -> Objects.equals(expert.getGender(), "男"));
        System.out.println("所有元素性别不为男："+noneMatch);

        //findFirst
        String name = experts.stream().findFirst().orElseGet(Expert::new).getName();
        System.out.println("获取第一个元素的姓名："+name);
        //findAny
        String anyName = experts.stream().findAny().orElseGet(Expert::new).getName();
        System.out.println("获取符合条件任意元素的姓名："+anyName);
        //count
        long count = experts.stream().filter(expert -> expert.getAge() > 23).count();
        System.out.println("年龄大于23岁的人员个数："+count);
        //max
        Expert maxExpert = experts.stream().max(Comparator.comparing(Expert::getAge)).get();
        System.out.println("年龄最大人员信息："+maxExpert);
        //min
        Expert minExpert = experts.stream().min(Comparator.comparing(Expert::getAge)).get();
        System.out.println("年龄最小人员信息："+minExpert);
        //forEach
        StringJoiner sj = new StringJoiner(",");
        experts.forEach(expert -> sj.add(expert.getName()));
        System.out.println("所有人员姓名："+sj.toString());
        //reduce
        int totalAge = experts.stream().mapToInt(Expert::getAge).reduce(0, (total, age) -> {
            total += age;
            return total;
        });
        System.out.println("所有人员年龄总和："+totalAge);

        //toSet
        Set<String> nameSet = experts.stream().map(Expert::getName).collect(Collectors.toSet());
        System.out.println("所有姓名："+nameSet);
        //groupingBy
        Map<String, List<Expert>> genderMap = experts.stream().collect(Collectors.groupingBy(Expert::getGender));
        System.out.println("性别分组信息："+genderMap);
        //summarizingDouble
        DoubleSummaryStatistics statistics = experts.stream().collect(Collectors.summarizingDouble(Expert::getAge));
        System.out.println("年龄最大值："+statistics.getMax());
        System.out.println("年龄最小值："+statistics.getMin());
        System.out.println("年龄平均值："+statistics.getAverage());
        System.out.println("年龄总和值："+statistics.getSum());
        System.out.println("总人数："+statistics.getCount());
        //partitioningBy
        Map<Boolean, List<Expert>> collect = experts.stream().collect(Collectors.partitioningBy(jia -> jia.getAge() > 23));
        List<Expert> gtExpert= collect.get(Boolean.TRUE);
        List<Expert> ltExpert= collect.get(Boolean.FALSE);
        System.out.println("年龄大于23岁的数据："+gtExpert);
        System.out.println("年龄小于等于23岁的数据："+ltExpert);

    }
}