package com.jiashn.springbootproject.redis.breakdown;

import com.jiashn.springbootproject.redis.utils.RedisData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiangjs
 * @description: 设置热门数据
 * @date: 2023/5/29 11:13
 **/
@Component
public class HotData implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(HotData.class);
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> dataMap = RedisData.DATA_MAP;
        dataMap.forEach((k,v) ->{
            double random = Math.ceil(10 * Math.random());
            long outTime = 10L + new Double(random).longValue();
            log.info("用户："+k+"，过期时间："+outTime);
            redisTemplate.opsForValue().set(k,v,outTime, TimeUnit.SECONDS);
        });
    }
}
