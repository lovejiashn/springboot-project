package com.jiashn.springbootproject.listener.service.impl;

import com.jiashn.springbootproject.listener.domain.AdminUser;
import com.jiashn.springbootproject.listener.domain.SelfDefineEvent;
import com.jiashn.springbootproject.listener.service.UserListenerService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/6/23 14:06
 **/
@Service
public class UserListenerServiceImpl implements UserListenerService {
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public ResultUtil<?> addUser() {
        AdminUser adminUser = new AdminUser("zhangsan","123456");
        return ResultUtil.success(adminUser);
    }

    @Override
    public ResultUtil<?> getUsers(HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        return ResultUtil.success(context.getAttribute("user"));
    }

    @Override
    public ResultUtil<?> selfDefineUser() {
        AdminUser adminUser = new AdminUser("queena","11111111");
        SelfDefineEvent defineEvent = new SelfDefineEvent(this, adminUser);
        applicationContext.publishEvent(defineEvent);
        return ResultUtil.success();
    }
}