package com.jiashn.springbootproject.redis.breakdown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/5/31 9:33
 **/
public class Locked {

    private final RedisTemplate<String,String> redisTemplate;

    public Locked(RedisTemplate<String,String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    private final static String HOT_KEY_LOCKED = "locked_hot_key";
    /**
     * 获取锁
     * @return 返回加锁状态
     */
    public boolean getLock(){
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(HOT_KEY_LOCKED, "locking", 30L, TimeUnit.SECONDS);
        return Objects.nonNull(absent) ? absent : false;
    }

    /**
     * 释放锁
     */
    public void releaseLock(){
        redisTemplate.delete(HOT_KEY_LOCKED);
    }
}
