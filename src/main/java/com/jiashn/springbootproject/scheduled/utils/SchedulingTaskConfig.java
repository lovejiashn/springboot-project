package com.jiashn.springbootproject.scheduled.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author: jiangjs
 * @description: 需要添加ThreadPoolTaskScheduler线程池才能正常通过@Autowired使用
 * @date: 2023/2/17 9:51
 **/
@Configuration
public class SchedulingTaskConfig {
    /**
     * 将任务放入map便于管理
     */
    public static ConcurrentHashMap<String, ScheduledFuture<?>> cache = new ConcurrentHashMap<>();

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(60);
        taskScheduler.setThreadNamePrefix("exch-task-scheduled-");
        taskScheduler.setAwaitTerminationSeconds(3000);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return taskScheduler;
    }
}
