package com.jiashn.springbootproject.redis.utils;

import com.jiashn.springbootproject.redis.domain.QueueTask;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiangjs
 * @description: 启动消费
 * @date: 2023/5/30 14:27
 **/
@Component
public class CustomerTaskLineRunner implements CommandLineRunner {

    @Resource
    private RedisTemplate<String,QueueTask<String>> redisTemplate;

    private final static String QUEUE_TYPE = QueueTypeEnum.ORDER.getType();
    private final static Logger log = LoggerFactory.getLogger(CustomerTaskLineRunner.class);

    @Override
    public void run(String... args) throws Exception {
        RedisQueueUtil<String> queueUtil = new RedisQueueUtil<>(QueueTypeEnum.ORDER,redisTemplate);
        while (true){
            Set<QueueTask<String>> queueTasks = queueUtil.loopGetTask(10);
            if (CollectionUtils.isNotEmpty(queueTasks)){
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
            log.info("------1分钟后再次获取------");
            Thread.sleep(60000);
        }
    }
}
