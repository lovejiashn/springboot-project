package com.jiashn.springbootproject.lock.distributed.redis.controller;

import com.jiashn.springbootproject.lock.distributed.redis.util.RedisDistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/4/14 9:54
 **/
@RestController
@RequestMapping("/lock")
public class RedisDistributedLockController {

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @GetMapping("/distributed/redis")
    public void executeRedisLock(){
        redisDistributedLock.executeLocked(() -> System.out.println("测试一下而已"));
    }
}
