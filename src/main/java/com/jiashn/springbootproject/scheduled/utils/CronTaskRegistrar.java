package com.jiashn.springbootproject.scheduled.utils;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: jiangjs
 * @description: 定时任务的添加、删除
 * @date: 2023/1/12 13:53
 **/
@Component
public class CronTaskRegistrar implements DisposableBean {

    private final Map<Runnable,ScheduledTask> scheduledTaskMap = new ConcurrentHashMap<>();

    @Resource
    private TaskScheduler taskScheduler;

    public void addCronTask(Runnable task,String cronExp){
        addCronTask(new CronTask(task,cronExp));
    }

    private void addCronTask(CronTask cronTask){
        if (Objects.nonNull(cronTask)){
            Runnable task = cronTask.getRunnable();
            //如果线程的map中存在任务，则删除
            if (this.scheduledTaskMap.containsKey(task)){
                removeCronTask(task);
            }
            //将任务添加入map中，其作用是便于管理
            this.scheduledTaskMap.put(task,scheduleCronTask(cronTask));
        }
    }

    public void removeCronTask(Runnable task){
        ScheduledTask scheduledTask = this.scheduledTaskMap.remove(task);
        if (Objects.nonNull(scheduledTask)){
            scheduledTask.cancel();
        }
    }

    public ScheduledTask scheduleCronTask(CronTask cronTask){
        //创建定时任务
        ScheduledTask scheduledTask = new ScheduledTask();
        //将定时任务线程与触发器加入schedule中
        scheduledTask.scheduledFuture = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }

    @Override
    public void destroy() throws Exception {

         //注销，则将map中所有的定时任务cancel掉
        for (ScheduledTask scheduledTask : this.scheduledTaskMap.values()) {
            scheduledTask.cancel();
        }
        this.scheduledTaskMap.clear();
    }
}