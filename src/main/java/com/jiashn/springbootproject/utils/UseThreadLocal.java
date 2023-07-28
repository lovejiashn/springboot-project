package com.jiashn.springbootproject.utils;

/**
 * @author: jiangjs
 * @description: 使用ThreadLocal
 * @date: 2023/7/28 14:55
 **/
public class UseThreadLocal {
    private static final ThreadLocal<String> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        tl.set("获取的值");
        new Thread(() -> {
            System.out.println("获取主线线程的值：" + tl.get());
        }).start();

        new Thread(() -> {
            tl.set("线程A名称【" + Thread.currentThread().getName() + "】");
            System.out.println("获取当前线程A的名称：" + tl.get());
            tl.remove();
            System.out.println("验证是否删除当前线程A的名称：" + tl.get());
        }).start();

        new Thread(() -> {
            tl.set("线程B名称【" + Thread.currentThread().getName()+"】");
            System.out.println("获取当前线程B的名称：" + tl.get());
            System.out.println("验证是否删除当前线程B的名称：" + tl.get());
        }).start();
    }
}
