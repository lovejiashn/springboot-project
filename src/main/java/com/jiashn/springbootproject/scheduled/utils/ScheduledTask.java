package com.jiashn.springbootproject.scheduled.utils;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/1/12 13:57
 **/
public final class ScheduledTask {
    volatile ScheduledFuture<?> scheduledFuture;

    /**
     * 取消定时任务
     */
    public void cancel(){
        ScheduledFuture<?> future = this.scheduledFuture;
        if (Objects.nonNull(future)){
            future.cancel(true);
        }
    }
}