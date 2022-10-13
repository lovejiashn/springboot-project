package com.jiashn.springbootproject.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author: jiangjs
 * @description: 链表、数组
 * @date: 2022/10/8 15:30
 **/
public class LinkedListTest {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.addFirst("Q");
        list.addLast("P");
        System.out.println("获取数据1：" + list);
        list.remove("Q");
        System.out.println("获取数据2：" + list);

        ArrayList<String> arrayList = new ArrayList<>();
        String[] val1 = new String[]{"AA","BB","CC","DD"};
        arrayList.add("aa");
        arrayList.add("bb");
        arrayList.add("cc");
        System.out.println("arrayList获取数据1：" + arrayList);
        System.arraycopy(val1,2,val1,1,2);
        System.out.println("val1：" + Arrays.toString(val1));
        System.out.println("val1----：" + val1.toString());
        arrayList.remove("bb");
        arrayList.remove(1);
        System.out.println("arrayList获取数据2" + arrayList);
    }
}