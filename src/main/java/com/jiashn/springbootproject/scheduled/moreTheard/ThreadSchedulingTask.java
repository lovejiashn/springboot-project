package com.jiashn.springbootproject.scheduled.moreTheard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/2/16 14:03
 **/
@Slf4j
@Component
public class ThreadSchedulingTask {

    @Async(value = "taskExecutor")
    @Scheduled(cron = "0/2 * * * * ? ")
    public void task(){
        Thread thread = Thread.currentThread();
        log.info(thread.getName() + "：线程，执行结果............");
    }
}
