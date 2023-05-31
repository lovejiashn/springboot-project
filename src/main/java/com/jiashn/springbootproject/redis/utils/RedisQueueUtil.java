package com.jiashn.springbootproject.redis.utils;

import com.jiashn.springbootproject.redis.domain.QueueTask;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiangjs
 * @description: redis实现消息队列
 * @date: 2023/5/30 10:51
 **/
public class RedisQueueUtil<T> {

    private static final Logger log = LoggerFactory.getLogger(RedisQueueUtil.class);

    private RedisTemplate<String,QueueTask<T>> redisTemplate;

    /**
     * 队列类型，即名称
     */
    private final QueueTypeEnum typeEnum;

    public RedisQueueUtil(QueueTypeEnum typeEnum,RedisTemplate<String,QueueTask<T>> redisTemplate){
        this.typeEnum = typeEnum;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加消息数据
     * @param queueTask 消息
     * @param time 延迟时间，单位s
     */
    public ResultUtil<String> sendQueueTask(QueueTask<T> queueTask, long time){
        //加锁
        if (getLock()){
            try {
                Boolean result = redisTemplate.opsForZSet().add(typeEnum.getType(), queueTask, System.currentTimeMillis() + time*1000);
                if (Objects.nonNull(result) && result){
                    log.info("添加消息数据成功：" + queueTask + "，添加时间：" + LocalDateTime.now());
                    return ResultUtil.success("添加消息数据成功");
                }
                return ResultUtil.error("添加消息数据失败");
            }finally {
                //释放锁
                releaseLock();
            }
        } else {
            log.info("未获取到锁，稍后再试");
            return ResultUtil.error("未获取到锁，稍后再试");
        }
    }

    /**
     * 获取zset前count数据
     * @param count 数据数
     * @return 返回获取到数据
     */
    public Set<QueueTask<T>> loopGetTask(int count) {
        while (true) {
            //rangeByScore，根据score顺序获取zset数据的值
            Set<QueueTask<T>> tasks = redisTemplate.opsForZSet().rangeByScore(typeEnum.getType(), 0, System.currentTimeMillis(), 0, count-1);
            //数据为空时，则休眠1s，然后再继续
            if (Objects.isNull(tasks) || tasks.isEmpty()){
                try {
                    log.info("------未获取消息任务------");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            return tasks;
        }
    }

    /**
     * 注销消息队列
     * @param typeEnum 消息队列名称
     */
    public void destroy(QueueTypeEnum typeEnum){
        redisTemplate.opsForZSet().remove(typeEnum.getType());
    }

    /**
     * 获取任务Id
     * @return 返回消息Id
     */
    public String getTaskId(){
       return typeEnum.getType() + "_" + UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 获取锁
     * @return 返回加锁状态
     */
    private boolean getLock(){
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(typeEnum.getType() + "_Locked", null, 30L, TimeUnit.MINUTES);
        return Objects.nonNull(absent) ? absent : false;
    }

    /**
     * 释放锁
     */
    public void releaseLock(){
        redisTemplate.delete(typeEnum.getType() + "_Locked");
    }
}
