package com.jiashn.springbootproject.listener.listener;

import com.jiashn.springbootproject.listener.domain.AdminUser;
import com.jiashn.springbootproject.listener.domain.SelfDefineEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjs
 * @description: 自定义监听器
 * @date: 2022/6/23 16:31
 **/
@Component
@Slf4j
public class SelfDefineEventListener implements ApplicationListener<SelfDefineEvent> {
    @Override
    public void onApplicationEvent(SelfDefineEvent event) {
        AdminUser user = event.getUser();
        log.info(String.format("用户名：%s,密码：%s",user.getUserName(),user.getPassword()));
    }
}