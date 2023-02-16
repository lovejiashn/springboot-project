package com.jiashn.springbootproject.scheduled.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/1/12 10:49
 **/
@Slf4j
public class ScheduledTaskRunnable implements Runnable {
    /**
     * 执行SQL
     */
    private final String sql;

    public ScheduledTaskRunnable(String sql){
        this.sql = sql;
    }

    @Override
    public void run() {
        try {
            Method method = ExecuteScheduledTask.class.getDeclaredMethod("executeSharedDataSql",String.class);
            ReflectionUtils.makeAccessible(method);
            method.invoke(ExecuteScheduledTask.class,sql);
        }catch (Exception e){
            log.error("获取线程方法报错：{}",e.getMessage());
            e.printStackTrace();
        }
    }
}