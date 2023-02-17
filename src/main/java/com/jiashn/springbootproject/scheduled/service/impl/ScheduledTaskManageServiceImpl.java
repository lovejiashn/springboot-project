package com.jiashn.springbootproject.scheduled.service.impl;

import com.jiashn.springbootproject.scheduled.service.ScheduledTaskManageService;
import com.jiashn.springbootproject.scheduled.utils.SchedulingTaskManage;
import com.jiashn.springbootproject.scheduled.utils.SchedulingTaskRunnable;
import com.jiashn.springbootproject.utils.ResultUtil;
import com.jiashn.springbootproject.valid.domain.UserInfo;
import org.apache.catalina.User;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/1/12 10:53
 **/
@Service
public class ScheduledTaskManageServiceImpl implements ScheduledTaskManageService {
    @Autowired
    private SchedulingTaskManage taskManage;
    @Override
    public ResultUtil<?> addTask() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("张三");
        userInfo.setPassword("121212121212");
        SchedulingTaskRunnable<UserInfo> taskRunnable = new SchedulingTaskRunnable<>(userInfo, "testSchedulingTask", "taskMethod");
        taskManage.createSchedulingTask("21212121212", taskRunnable,"0/10 * * * * ?");
        return ResultUtil.success();
    }

    @Override
    public ResultUtil<?> deleteTask() {
        String key = "21212121212";
        taskManage.stopSchedulingTask(key);
        return ResultUtil.success();
    }
}