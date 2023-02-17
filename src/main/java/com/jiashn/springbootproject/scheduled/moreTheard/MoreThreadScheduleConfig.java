package com.jiashn.springbootproject.scheduled.moreTheard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: jiangjs
 * @description: 定时任务实现多线程，防止阻塞
 *               // @Configuration:相当于以前Spring的xml配置文件中的<beans></beans>的java实现
 *               // @Configurable：主要使用于某些自己new出来的对象，而这个对象又必须依赖Spring容器的对象才能完成一些工作。
 * @date: 2023/2/16 11:23
 **/
@Configuration
@EnableScheduling
public class MoreThreadScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
    }

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setKeepAliveSeconds(120);
        //设置队列容量
        executor.setQueueCapacity(40);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //等待所有任务结束后关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }


}
