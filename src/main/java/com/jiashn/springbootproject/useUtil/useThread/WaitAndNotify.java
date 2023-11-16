package com.jiashn.springbootproject.useUtil.useThread;

import com.aspose.words.Run;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author: jiangjs
 * @description: 线程的wait()与notify()
 * @date: 2023/5/10 15:39
 **/
public class WaitAndNotify {
    static boolean flag = true;
    static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "WaitThread");
        notifyThread.start();
    }

    static class Wait implements Runnable{
        @SneakyThrows
        @Override
        public void run() {
            //获取锁
            synchronized (lock){
                //条件不满足时，继续wait,同时释放锁
                while (flag){
                    System.out.println("flag:"+flag+",wait(),继续等待......。时间：" + System.currentTimeMillis());
                    lock.wait();
                }
                //条件满足
                System.out.println("条件满足，工作了......。时间：" + System.currentTimeMillis());
            }

        }
    }

    static class Notify implements Runnable{
        @SneakyThrows
        @Override
        public void run() {
            //获取锁
            synchronized (lock){
                //进行通知，通知时不会释放lock的锁
                lock.notifyAll();
                flag = false;
                TimeUnit.SECONDS.sleep(5);
                System.out.println("flag:"+flag+",notifyAll(),通知了.......。时间：" + System.currentTimeMillis());
            }
            synchronized (lock){
                System.out.println("再次加锁......。时间：" + System.currentTimeMillis());
            }

        }
    }
}
