package com.jiashn.springbootproject.redis.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/5/29 15:26
 **/
public class RedisData {

    public static final Map<String,String> DATA_MAP = new HashMap<>();
    static {
        DATA_MAP.put("10001","用户信息：10001");
        DATA_MAP.put("10002","用户信息：10002");
    }
}
