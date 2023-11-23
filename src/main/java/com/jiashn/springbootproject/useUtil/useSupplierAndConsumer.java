package com.jiashn.springbootproject.useUtil;


import cn.hutool.core.io.LineHandler;
import com.jiashn.springbootproject.lock.distributed.redis.util.ExecuteBusiness;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author: jiangjs
 * @description: 使用Supplier、Consumer
 *              Supplier：供给型接口，即不需要传入参数，直接执行lambda表达式返回执行结果；
 *              Consumer：消费型接口，即需要传入参数，执行lambda表达式不需要返回值返回。
 * @date: 2023/4/26 9:53
 **/
public class useSupplierAndConsumer {
    public static void main(String[] args) {
        String taskCode = TaskCodeEnum.SUB_TASK.getTaskCode().get();
        System.out.println("获取任务编码结果：" + taskCode);

        List<String> data = Arrays.asList("张无忌","张翠山","张三丰","小昭","赵敏","白眉鹰王");
        //使用Supplier获取姓张的人
        Supplier<List<String>> supplier = () -> {
            List<String> filter = new ArrayList<>();
            for (String datum : data) {
                if (datum.startsWith("张")){
                    filter.add(datum);
                }
            }
            return filter;
        };
        List<String> result = supplier.get();
        System.out.println("获取Supplier结果：" + result);
        //使用Consumer打印姓张的人，由于Consumer没有返回值，只能打印结果
        Consumer<String> consumer = (name) -> {
            if (name.startsWith("张")){
                System.out.println("张姓人员：" + name);
            }
        };

        Consumer<String> secConsumer = (name) -> {
            if (Objects.equals("张翠山",name)){
                System.out.println("找到了：" + name);
            }
        };
        Objects.requireNonNull(consumer);
        for (String datum : data) {
            moreAndThen(datum,consumer,secConsumer);
        }

        //使用自定义函数式编程接口
        ExecuteBusiness eb = () -> {
            StringJoiner sj = new StringJoiner(",");
            for (String datum : data) {
                sj.add(datum);
            }
            System.out.println("武侠人物：" + sj.toString());
        };
        eb.execute();
    }

    @SafeVarargs
    private static void moreAndThen(String data, Consumer<String>... consumers){
        Consumer<String> exeConsumer = null;
        for (Consumer<String> consumer : consumers) {
            if (Objects.isNull(exeConsumer)){
                exeConsumer = consumer;
                continue;
            }
            exeConsumer = exeConsumer.andThen(consumer);
        }
        assert exeConsumer != null;
        exeConsumer.accept(data);
    }
}
