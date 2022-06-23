package com.jiashn.springbootproject.listener.service;

import com.jiashn.springbootproject.utils.ResultUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/6/23 14:06
 **/
public interface UserListenerService {

    /**
     * 添加用户
     * @return 返回结果
     */
    ResultUtil<?> addUser();

    /**
     * 获取用户信息
     * @param request 请求
     * @return 返回结果
     */
    ResultUtil<?> getUsers(HttpServletRequest request);

    /**
     * 自定义监听器
     * @return 返回结果
     */
    ResultUtil<?> selfDefineUser();
}
