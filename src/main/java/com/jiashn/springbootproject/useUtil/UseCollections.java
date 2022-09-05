package com.jiashn.springbootproject.useUtil;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: jiangjs
 * @description: 使用Collections,CollectionUtils各种方法
 * @date: 2022/7/7 14:46
 **/
public class UseCollections {

    public static void main(String[] args) {
        List<Integer> params = Arrays.asList(1, 3, 5, 7, 8, 9, 2, 10);
        List<Map<String, String>> res = new ArrayList<>();
        Map<String, String> resMap = new HashMap<>();
        Map<String, String> resMap2 = new HashMap<>();
        resMap.put("answer","2121");
        resMap2.put("answer","4444");
        res.add(resMap2);
        res.add(resMap);
        //升序
        Collections.sort(params);
        System.out.println("升序："+ params);
        //降序
        Collections.reverse(params);
        System.out.println("降序："+ params);
        //二分查找，集合需要进行排序，否则无效，无法确定哪些元素存在
        int i = Collections.binarySearch(params, 10);
        System.out.println("查找数据："+ i);

        //空集合
        List<Integer> empties = Collections.emptyList();
        System.out.println("空集合："+ empties);
        //数据交换位置
        Collections.swap(params,0,params.size() -1);
        System.out.println("交换位置集合："+ params);

        //数据复制,dest：目标集合，src:源集合
        System.out.println("源集合大小：" + params.size());
        List<Integer> copyData = new ArrayList<>();
        Collections.addAll(copyData,new Integer[params.size()]);
        Collections.copy(copyData,params);
        copyData.add(11);
        System.out.println("拷贝后数据值："+ copyData);

        //获取最大值
        Integer max = Collections.max(copyData);
        System.out.println("最大值："+ max);

        //获取最小值
        Integer min = Collections.min(copyData);
        System.out.println("最小值："+ min);

         //出现的次数
        copyData.add(9);
        copyData.add(9);
        int frequency = Collections.frequency(copyData, 9);
        System.out.println("出现次数："+ frequency);

        //使用CollectionUtils
        String[] names = {"张三", "李四", "王五"};
        List<?> objects = org.springframework.util.CollectionUtils.arrayToList(names);
        System.out.println("数组转list:"+objects);
        //验证是否存在值
        boolean isExist = CollectionUtils.containsAny(objects, Collections.singletonList("张三"));
        System.out.println("验证是否存在:"+isExist);

        //集合操作
        //合并
        List<Integer> ones = Arrays.asList(1,3,5,7,9);
        List<Integer> twos = Arrays.asList(2,4,6,8,10,9);
        List<Object> unions = Arrays.asList(CollectionUtils.union(ones, twos).toArray());
        System.out.println("合并:"+unions);
        //交集
        List<Object> intersections = Arrays.asList(CollectionUtils.intersection(ones, twos).toArray());
        System.out.println("交集:"+intersections);
        //补集
        List<Object> disjunctions = Arrays.asList(CollectionUtils.disjunction(ones, twos).toArray());
        System.out.println("补集:"+disjunctions);
        //差集
        List<Object> subtracts = Arrays.asList(CollectionUtils.subtract(ones, twos).toArray());
        System.out.println("差集:"+subtracts);
        /*//数据类型转换
        CollectionUtils.transform(ones, String::valueOf);
        System.out.println("转换后集合:"+ones);*/

        //合并排序
        List<String> userNames = new ArrayList<>();
        userNames.add("张三");
        userNames.add("李四");
        userNames.add("王五");
        CollectionUtils.addIgnoreNull(userNames,"赵六");
        CollectionUtils.addIgnoreNull(userNames,"");
        CollectionUtils.addIgnoreNull(userNames,null);
        System.out.println("添加非空数据:"+userNames);

        //集合是否相等
        List<Integer> threes = Arrays.asList(1,3,5,7,9);
        boolean equalCollection = CollectionUtils.isEqualCollection(ones, twos);
        boolean isEqual = CollectionUtils.isEqualCollection(ones, threes);
        System.out.println("集合是否相等:"+equalCollection+";相等否："+isEqual);


        System.out.println("统计结果:"+ res.stream().map(map -> map.get("answer")).collect(Collectors.joining(";")));

        //使用for循环删除数据
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);
        ints.add(1);
        int size = ints.size();
        List<Integer> subInts = ints.subList(2,size);
        System.out.println("截取子集合:"+subInts.toString());

        ints.removeIf(next -> 1 == next);
        System.out.println("删除数据成功！"+ints.toString());


    }
}