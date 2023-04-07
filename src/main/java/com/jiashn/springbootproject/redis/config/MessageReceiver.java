package com.jiashn.springbootproject.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/4/7 14:32
 **/
@Slf4j
@Component
public class MessageReceiver {

    public void receive(String message,String channel) {
        log.info("------频道名称------："+ channel);
        log.info("------消息内容------："+ message);
    }
}
