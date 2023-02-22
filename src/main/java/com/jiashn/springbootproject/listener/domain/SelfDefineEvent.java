package com.jiashn.springbootproject.listener.domain;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/6/23 16:32
 **/
@Getter
public class SelfDefineEvent extends ApplicationEvent {

    private AdminUser user;

    public SelfDefineEvent(Object source,AdminUser user) {
        super(source);
        this.user = user;
    }
}