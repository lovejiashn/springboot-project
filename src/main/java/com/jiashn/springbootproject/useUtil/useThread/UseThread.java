package com.jiashn.springbootproject.useUtil.useThread;

import com.alibaba.druid.util.DaemonThreadFactory;
import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.TimeUnit;

/**
 * @author: jiangjs
 * @description: 线程使用
 * @date: 2023/5/9 10:57
 **/
public class UseThread {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("-------------线程1---------------");
        });
        thread.setDaemon(Boolean.TRUE);
        Thread thread2 = new Thread(() -> {
            System.out.println("-------------线程2---------------");
        });
        thread2.setDaemon(Boolean.TRUE);
        thread2.start();
        thread2.join();
        thread.start();
        thread.join();

    }
}
