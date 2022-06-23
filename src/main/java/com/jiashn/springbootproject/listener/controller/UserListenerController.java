package com.jiashn.springbootproject.listener.controller;

import com.jiashn.springbootproject.listener.service.UserListenerService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: jiangjs
 * @description: 监听器使用
 * @date: 2022/6/23 14:03
 **/
@RestController
@RequestMapping("/listener")
public class UserListenerController {

    @Resource
    private UserListenerService userListenerService;

    @PostMapping("/addUser.do")
    public ResultUtil<?> addUser(){
        return userListenerService.addUser();
    }

    @GetMapping("/getUsers.do")
    public ResultUtil<?> getUsers(HttpServletRequest request){
        return userListenerService.getUsers(request);
    }

    @GetMapping("/selfDefineUser.do")
    public ResultUtil<?> selfDefineUser(){
        return userListenerService.selfDefineUser();
    }
}