package com.jiashn.springbootproject.scheduled.controller;

import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jiangjs
 * @description: 定时任务管理类
 * @date: 2023/1/12 10:35
 **/
@RestController
@RequestMapping("/scheduled")
public class ScheduledTaskManageController {

    @GetMapping("/addTask.do")
    public ResultUtil<?> addTask(){
        return ResultUtil.success();
    }
}