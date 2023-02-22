package com.jiashn.springbootproject.listener.service.impl;

import com.jiashn.springbootproject.listener.domain.AdminUser;
import com.jiashn.springbootproject.listener.domain.SelfDefineEvent;
import com.jiashn.springbootproject.listener.service.UserListenerService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/6/23 14:06
 **/
@Service
public class UserListenerServiceImpl implements UserListenerService {
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

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
        System.out.println(applicationContext.getMessage("hi", null, Locale.CHINA));
        eventPublisher.publishEvent(defineEvent);
        return ResultUtil.success();
    }
}