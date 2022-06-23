package com.jiashn.springbootproject.listener.domain;
import org.springframework.context.ApplicationEvent;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/6/23 16:32
 **/
public class SelfDefineEvent extends ApplicationEvent {

    private AdminUser user;

    public SelfDefineEvent(Object source,AdminUser user) {
        super(source);
        this.user = user;
    }

    public AdminUser getUser() {
        return user;
    }

    public void setUser(AdminUser user) {
        this.user = user;
    }
}