package com.jiashn.springbootproject.listener.listener;

import com.jiashn.springbootproject.listener.service.UserListenerService;
import com.jiashn.springbootproject.listener.service.impl.UserListenerServiceImpl;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * @author: jiangjs
 * @description: ApplicationListener 初始化一些数据到application1域中的监听器
 * @date: 2022/6/23 14:12
 **/
@Component
public class ServiceContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Cacheable(value = "getUsers")
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        UserListenerService service = context.getBean(UserListenerServiceImpl.class);
        ResultUtil<?> resultUtil = service.addUser();
        ServletContext bean = context.getBean(ServletContext.class);
        bean.setAttribute("user",resultUtil.getData());
    }
}