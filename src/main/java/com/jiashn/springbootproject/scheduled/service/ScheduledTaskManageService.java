package com.jiashn.springbootproject.scheduled.service;

import com.jiashn.springbootproject.utils.ResultUtil;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/1/12 10:52
 **/
public interface ScheduledTaskManageService {
    /**
     * 添加任务
     * @return 返回添加结果
     */
    ResultUtil<?> addTask();
    /**
     * 删除任务
     * @return 返回添加结果
     */
    ResultUtil<?> deleteTask();
}
