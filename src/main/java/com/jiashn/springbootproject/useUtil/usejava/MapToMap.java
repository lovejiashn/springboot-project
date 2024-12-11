package com.jiashn.springbootproject.useUtil.usejava;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: jiangjs
 * @description: 不同map类型相互转换
 * @date: 2024/12/10 15:50
 **/
public class MapToMap {
    public static void main(String[] args) {
        HashMap<String, Integer> maps = new HashMap<String, Integer>() {{
            put("a", 1);
            put("b", 2);
            put("c", 3);
        }};

        //转成Map<String,String>
        //使用stream
        Map<String, String> strMap = maps.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, s -> String.valueOf(s.getValue())));
        System.out.println("stream转Map:"+strMap);
        //使用trasform方式
        Map<String, String> stringMap = Maps.transformValues(maps, String::valueOf);
        System.out.println("trasform转map："+stringMap);

        //自定义转换
        Map<String, String> transform = transform(maps, String::valueOf);
        System.out.println("自定义转换："+transform);
    }

    private static <K,T,V> Map<K,V> transform(Map<K,T> map, Function<T,V> function) {
        return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> function.apply(e.getValue())));
    }
}
