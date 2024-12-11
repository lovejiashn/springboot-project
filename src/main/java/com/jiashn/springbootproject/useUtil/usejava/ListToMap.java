package com.jiashn.springbootproject.useUtil.usejava;

import com.google.common.collect.HashMultimap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: jiangjs
 * @description: list转Map
 * @date: 2024/12/10 16:31
 **/
public class ListToMap {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("1","2","3"));
        Map<Integer, List<String>> collect = list.stream().collect(Collectors.groupingBy(String::length, Collectors.mapping(s -> s, Collectors.toList())));
        System.out.println(collect);

        //HashMultimap使用
        HashMultimap<Integer, String> map = HashMultimap.create();
        int size = list.size();
        for (int i = 1; i <= size; i++) {
            map.put(i,list.get(i-1));
        }
        System.out.println(map);

    }

    private static <K,I,V> Map<K,List<V>> toMap(List<I> list, Function<I,K> keyFun,Function<I,V> valueFun){
        return list.stream().collect(Collectors.groupingBy(keyFun, Collectors.mapping(valueFun, Collectors.toList())));
    }
}
