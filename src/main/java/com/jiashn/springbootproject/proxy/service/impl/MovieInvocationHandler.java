package com.jiashn.springbootproject.proxy.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/9 16:43
 **/
@Slf4j
public class MovieInvocationHandler implements InvocationHandler {

    private Object object;

    public MovieInvocationHandler(Object object){
        this.object = object;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        startMovie();
        Object invoke = method.invoke(object, args);
        endMovie();
        return invoke;
    }

    private void startMovie(){
        log.info("放映前：广告播放中......");
    }

    private void endMovie(){
        log.info("放映后：影片已放完，广告播放中......");
    }
}