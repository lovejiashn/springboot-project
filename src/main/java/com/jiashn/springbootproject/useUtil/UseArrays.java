package com.jiashn.springbootproject.useUtil;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/9/4 11:04
 **/
public class UseArrays {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        Integer[] arr2 = new Integer[]{1,2,3,4,5};
        // copyOf，复制指定的数组中个数元素形成新数组，截取或不足位用0填充
        //copyOfRange，复制指定范围内的数组到一个新的数组，超出范围补0。
        int[] ints = Arrays.copyOf(arr, 7);
        int[] ints1 = Arrays.copyOfRange(arr, 2, 7);
        System.out.println("copyOf：" + Arrays.toString(ints));
        System.out.println("copyOfRange：" + Arrays.toString(ints1));
        //fill：填充数组,填充相同元素
        int[] ints2 = new int[4];
        Arrays.fill(ints2,1);
        System.out.println("fill：" + Arrays.toString(ints2));
        //equals：比较两个数组是否相同
        boolean equals = Arrays.equals(arr, ints2);
        System.out.println("equals：" + equals);
        //sort：对数组进行排序，默认升序
        Arrays.sort(arr);
        Arrays.sort(arr2, Collections.reverseOrder());
        System.out.println("sort：" + Arrays.toString(arr));
        System.out.println("sort reverse：" + Arrays.toString(arr2));
        //binarySearch：二分查找，返回索引位置,未找到时则小于0
        int i = Arrays.binarySearch(arr, 7);
        System.out.println("binarySearch：" + i);
        //setAll：对数组进行赋值，默认为0
        Arrays.setAll(arr2, p -> p*10);
        System.out.println("setAll：" + Arrays.toString(arr2));
        //parallelPrefix：点前元素与前面元素之和并赋值当前元素
        Arrays.parallelPrefix(arr, Integer::sum);
        System.out.println("parallelPrefix：" + Arrays.toString(arr));
    }
}
