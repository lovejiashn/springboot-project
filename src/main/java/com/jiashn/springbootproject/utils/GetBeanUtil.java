package com.jiashn.springbootproject.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/23 16:06
 **/
@Component
public class GetBeanUtil implements ApplicationContextAware {
    protected static ApplicationContext context = null;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Objects.isNull(context)){
            context = applicationContext;
        }
    }

    public static <T> T getBean(Class<T> c){

        return context.getBean(c);
    }
}