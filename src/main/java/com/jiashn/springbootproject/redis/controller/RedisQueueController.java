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
            if (num == 3){
                log.info("已重试三次，程序结束......");
                resultUtil = ResultUtil.error("发送消息失败");
                break;
            }
            resultUtil = queueUtil.sendQueueTask(task,10);
            if (resultUtil.getCode() == 6000){
                log.info("消息重复添加，程序结束......");
                break;
            }
            if (resultUtil.getCode() != 1000){
                num ++;
                try {
                    log.info("未获取到锁，休眠10s，再继续......");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                log.info("消息发送成功，程序结束......");
                break;
            }
        }
        return resultUtil;
    }
}
