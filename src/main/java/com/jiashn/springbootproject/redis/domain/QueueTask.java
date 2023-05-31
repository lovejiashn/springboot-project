package com.jiashn.springbootproject.redis.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: jiangjs
 * @description: 消息实体
 * @date: 2023/5/30 11:11
 **/
@Data
@Accessors(chain = true)
public class QueueTask<T> {
    /**
     * 消息Id
     */
    private String taskId;
    /**
     * 任务
     */
    private T task;
}
