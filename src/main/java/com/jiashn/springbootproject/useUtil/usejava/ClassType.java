package com.jiashn.springbootproject.useUtil.usejava;

/**
 * @author: jiangjs
 * @description: 判断定义的数据类型是否为包装类还是基础类
 * @date: 2024/12/10 17:19
 **/
public class ClassType {
    public static void main(String[] args) {
        int num = 123;
        Integer num2 = 1234;
       // System.out.println(isPrimitive(num.class));
        System.out.println(isPrimitive(num2.getClass()));

    }

    private static boolean isPrimitive(Class<?> clazz){
       return clazz.isPrimitive();
    }
}
