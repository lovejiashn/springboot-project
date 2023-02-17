package com.jiashn.springbootproject.scheduled.controller;

import com.jiashn.springbootproject.scheduled.service.ScheduledTaskManageService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: jiangjs
 * @description: 定时任务管理类
 * @date: 2023/1/12 10:35
 **/
@RestController
@RequestMapping("/scheduled")
public class ScheduledTaskManageController {

    @Autowired
    private ScheduledTaskManageService manageService;

    @GetMapping("/addTask.do")
    public ResultUtil<?> addTask(){
        return manageService.addTask();
    }

    @GetMapping("/deleteTask.do")
    public ResultUtil<?> deleteTask(){
        return manageService.deleteTask();
    }
}