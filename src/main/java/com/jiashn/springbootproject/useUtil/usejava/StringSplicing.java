package com.jiashn.springbootproject.useUtil.usejava;

import java.text.MessageFormat;

/**
 * @author: jiangjs
 * @description: 字符串拼接 下划线
 * @date: 2024/12/10 10:45
 **/
public class StringSplicing {
    public static void main(String[] args) {
        //String.format使用%s拼接，需%s个数与拼接字符数一致或拼接字符数量多于%s
        System.out.printf("hello %s,%s%n","moto","哟吼");

        //使用MessageFormat拼接字符，需通过{}，且里面是数字(从0开始)进行拼接。
        System.out.println(MessageFormat.format("hello {0} {1}","moto","哟吼"));

    }
}
