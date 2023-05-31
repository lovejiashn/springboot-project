package com.jiashn.springbootproject.redis.controller;

import com.jiashn.springbootproject.redis.domain.QueueTask;
import com.jiashn.springbootproject.redis.utils.QueueTypeEnum;
import com.jiashn.springbootproject.redis.utils.RedisQueueUtil;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiangjs
 * @description: redis实现消息队列
 * @date: 2023/5/30 14:20
 **/
@RestController
@RequestMapping("/queue")
public class RedisQueueController {

    @Resource
    private RedisTemplate<String,QueueTask<String>> redisTemplate;
    private final static Logger log = LoggerFactory.getLogger(RedisQueueController.class);
    private final static String QUEUE_TYPE = QueueTypeEnum.ORDER.getType();

    @GetMapping("/getTaskId.do")
    public ResultUtil<?> getTaskId(){
        return ResultUtil.success(new RedisQueueUtil<>(QueueTypeEnum.ORDER,redisTemplate).getTaskId());
    }

    @PostMapping("/sendTask.do")
    public ResultUtil<?> sendTask(@RequestBody QueueTask<String> task){
        RedisQueueUtil<String> queueUtil = new RedisQueueUtil<>(QueueTypeEnum.ORDER, redisTemplate);
        int num = 0;
        ResultUtil<String> resultUtil;
        while (true){
            resultUtil = queueUtil.sendQueueTask(task,10);
            if (resultUtil.getCode() != 1000){
                if (num == 3){
                    log.info("已重试三次，程序结束......");
                    break;
                }
                num ++;
                try {
                    log.info("未获取到锁，休眠10s，再继续......");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultUtil;
    }
    @GetMapping("/customerTask.do")
    public void customerTask(){
        RedisQueueUtil<String> queueUtil = new RedisQueueUtil<>(QueueTypeEnum.ORDER,redisTemplate);
        Set<QueueTask<String>> queueTasks = queueUtil.loopGetTask(10);
        for (QueueTask<String> queueTask : queueTasks) {
            //校验当前消息是否已消费，主要防止网络延时，导致多次提交同一任务 存在
            QueueTask<String> stringQueueTask = redisTemplate.opsForValue().get(QUEUE_TYPE + "_" + queueTask.getTaskId());
            if (Objects.nonNull(stringQueueTask)){
                log.info("该任务已经消费，不能重复消费");
                redisTemplate.opsForZSet().remove(QUEUE_TYPE,queueTask);
                continue;
            }
            Long removeNum = redisTemplate.opsForZSet().remove(QUEUE_TYPE,queueTask);
            if (Objects.nonNull(removeNum) && removeNum > 0){
                String task = queueTask.getTask();
                log.info("消费任务数据：" + task);
                //设置过期时间，10分钟内则默认是重复提交
                redisTemplate.opsForValue().set(QUEUE_TYPE + "_" + queueTask.getTaskId(),queueTask,10L, TimeUnit.MINUTES);
            }
        }
    }
}
