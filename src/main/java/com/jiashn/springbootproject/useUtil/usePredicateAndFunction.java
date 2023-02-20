package com.jiashn.springbootproject.useUtil;

import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author: jiangjs
 * @description: 使用Predicate和Function
 * @date: 2023/2/17 16:06
 **/
public class usePredicateAndFunction {

    public static void main(String[] args) {
        int len = strApply(String::length,"word");
        System.out.println("字符串长度："+len);
        Integer compose = moreCompose(5, i -> i + 1, i -> i * 5);
        System.out.println("compose:先计算输入逻辑，再计算当前逻辑："+compose);

        Integer andThen = moreAndThen(5, i -> i + 1, i -> i * 5);
        System.out.println("andThen:先计算当前逻辑，再计算传入逻辑："+andThen);


        boolean conform = conformLength(s -> s.length() > 5, "helloWord");
        System.out.println("字符串长度是否符合："+conform);
        boolean contain = moreContain("helloWord", s -> s.contains("h"),
                s -> s.contains("W"),
                s -> s.contains("Word"));
        System.out.println("字符串是否包含多条件："+contain);

        boolean single = singleContain("helloWord", s -> s.contains("hello"),
                s -> s.contains("b"),
                s -> s.contains("a"));
        System.out.println("字符串是否包含单条件："+single);
    }

    /**
     * 字段长度
     */
    public static Integer strApply(Function<String,Integer> func,String word){
        return func.apply(word);
    }

    @SafeVarargs
    public static Integer moreCompose(Integer initVal, Function<Integer, Integer>... func){
        Function<Integer, Integer> fir = null;
        AtomicInteger ai = new AtomicInteger(0);
        for (Function<Integer, Integer> function : func) {
            int val = ai.intValue();
            fir = val ==0 ? function : fir.compose(function);
            ai.incrementAndGet();
        }
        assert fir != null;
        return fir.apply(initVal);
    }

    @SafeVarargs
    public static Integer moreAndThen(Integer initVal, Function<Integer, Integer>... func){
        Function<Integer, Integer> fir = null;
        AtomicInteger ai = new AtomicInteger(0);
        for (Function<Integer, Integer> function : func) {
            int val = ai.intValue();
            fir = val ==0 ? function : fir.andThen(function);
            ai.incrementAndGet();
        }
        assert fir != null;
        return fir.apply(initVal);
    }

    /**
     * 是否匹配长度
     */
    public static boolean conformLength(Predicate<String> predicate,String word){
        return predicate.test(word);
    }

    /**
     * 多条件匹配
     */
    @SafeVarargs
    public static boolean moreContain(String data, Predicate<String>... predicates){
        Predicate<String> more = null;
        AtomicInteger ai = new AtomicInteger(0);
        for (Predicate<String> predicate : predicates) {
            int val = ai.intValue();
            more = val == 0 ? predicate : more.and(predicate);
            ai.incrementAndGet();
        }
        assert more != null;
        return more.test(data);
    }
    /**
     * 多条件匹配
     */
    @SafeVarargs
    public static boolean singleContain(String data, Predicate<String>... predicates){
        Predicate<String> single = null;
        AtomicInteger ai = new AtomicInteger(0);
        for (Predicate<String> predicate : predicates) {
            int val = ai.intValue();
            single = val == 0 ? predicate : single.or(predicate);
            ai.incrementAndGet();
        }
        assert single != null;
        return single.test(data);
    }
}
