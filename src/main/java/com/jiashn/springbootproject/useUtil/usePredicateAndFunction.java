package com.jiashn.springbootproject.useUtil;

import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/2/17 16:06
 **/
public class usePredicateAndFunction {

    public static void main(String[] args) {
        int len = strLength(String::length,"word");
        System.out.println("字符串长度："+len);
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
    public static Integer strLength(Function<String,Integer> func,String word){
        return func.apply(word);
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
            if (val == 0){
                more =  predicate;
            }
            more = more.and(predicate);
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
            if (val == 0){
                single =  predicate;
            }
            single = single.or(predicate);
            ai.incrementAndGet();
        }
        assert single != null;
        return single.test(data);
    }
}
