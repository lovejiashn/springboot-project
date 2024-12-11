package com.jiashn.springbootproject.useUtil.usejava;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * @author: jiangjs
 * @description: 不可变量
 * @date: 2024/12/10 15:40
 **/
public class Immutable {

    public static void main(String[] args) {
        ImmutableList<Integer> nums = ImmutableList.of(1,2,3);
        ImmutableSet<Integer> numSet = ImmutableSet.of(1,2,3);
        ImmutableMap<String, Integer> of = ImmutableMap.of("hello", 1, "moto", 2);
        System.out.println(nums);
        System.out.println(numSet);
        System.out.println(of);
    }
}
