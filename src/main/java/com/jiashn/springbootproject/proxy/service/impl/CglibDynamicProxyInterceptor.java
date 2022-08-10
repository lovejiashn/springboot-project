package com.jiashn.springbootproject.proxy.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/10 10:11
 **/
@Slf4j
public class CglibDynamicProxyInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        startMovie();
        Object object = methodProxy.invokeSuper(o, objects);
        endMovie();
        return object;
    }

    private void startMovie(){
        log.info("放映前：广告播放中......");
    }

    private void endMovie(){
        log.info("放映后：影片已放完，广告播放中......");
    }
}