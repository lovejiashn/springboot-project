package com.jiashn.springbootproject.lock.distributed.redis.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


/**
 * @author: jiangjs
 * @description: redis实现分布式锁
 * @date: 2023/4/14 9:10
 **/
@Component
@Slf4j
public class RedisDistributedLock {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 锁名称，即redis中保存数据key的名称
     */
    private static final String LOCK_KEY = "distribute_key";

    public void executeLocked(ExecuteBusiness eb){
        String uuid = String.valueOf(UUID.randomUUID()).replaceAll("-","");
        //获取锁，有效期10s
        Boolean absent = this.redisTemplate.opsForValue().setIfAbsent(LOCK_KEY, uuid, 10, TimeUnit.SECONDS);
        if (Objects.nonNull(absent) && absent){
            //执行业务逻辑
            eb.execute();
            //Lua脚本释放锁（保证原子性，防止误删）
            String script = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
            redisScript.setScriptText(script);
            redisScript.setResultType(Long.class);
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(LOCK_KEY), uuid);
            log.info("加锁成功，返回值：{}",result);
        } else {
            log.info("加锁失败");
        }

    }
}
