package com.jiashn.springbootproject.useUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: jiangjs
 * @description: 使用Atomic（ AtomicInteger，AtomicLong等）
 * @date: 2022/9/8 15:41
 **/
public class UseAtomic {

    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger(10);
        List<String> params = Arrays.asList("I","Love","Queena");
        params.forEach(val ->{
           //addAndGet(n)：给定添加的值，且添加后返回新值，相当于 i += n;
           //getAndAdd(n)：给定添加的值，且添加后返回旧值，即首次获取到数据为定义的值;
           //incrementAndGet()：自增，每次加1添加后返回新值，相当于 ++i;
           //getAndIncrement()：自增，每次加1添加后返回旧值，相当于 i++;
           //decrementAndGet()：自减，每次减1添加后返回新值，相当于 --i;
           //getAndDecrement()：自减，每次减1添加后返回旧值，相当于 i--;
            System.out.println("排序：" + ai.intValue() + ";值："+val);
        });
        //compareAndSet(expect,update)：即expect与初始定义的值是否相等，相等时则将update赋值给定义的变量
        boolean value = ai.compareAndSet(10, 20);
        System.out.println("比较结果One："+value+";值：" + ai.intValue());
        boolean selVal = ai.compareAndSet(20, 30);
        System.out.println("比较结果Tow："+selVal+";值：" + ai.intValue());
    }
}