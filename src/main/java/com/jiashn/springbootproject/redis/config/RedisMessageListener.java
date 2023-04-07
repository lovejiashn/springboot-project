package com.jiashn.springbootproject.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author: jiangjs
 * @description: redis消息监听类
 * @date: 2023/4/7 11:10
 **/
@Slf4j
@Component
public class RedisMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        log.info("------频道名称------："+new String(channel));
        log.info("------消息内容------："+new String(body));
    }
}
