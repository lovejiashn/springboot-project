package com.jiashn.springbootproject.redis.config;

import com.jiashn.springbootproject.redis.utils.RedisData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/5/29 15:24
 **/
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private final static Logger log = LoggerFactory.getLogger(RedisKeyExpirationListener.class);
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();
        log.info("key：" + key + "过期，重新加载数据，设置过期时间");
        String val = RedisData.DATA_MAP.get(key);
        double random = Math.ceil(10 * Math.random());
        long outTime = 10L + new Double(random).longValue();
        log.info("设置用户："+key+"，过期时间："+outTime);
        redisTemplate.opsForValue().set(key,val,outTime, TimeUnit.SECONDS);
        super.onMessage(message, pattern);
    }
}
