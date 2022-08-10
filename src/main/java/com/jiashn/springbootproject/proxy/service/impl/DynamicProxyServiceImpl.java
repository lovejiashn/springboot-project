package com.jiashn.springbootproject.proxy.service.impl;

import com.jiashn.springbootproject.proxy.service.DynamicProxyService;
import com.jiashn.springbootproject.proxy.service.Movie;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.FixedValue;
import org.springframework.stereotype.Service;

import java.lang.reflect.Proxy;

/**
 * @author: jiangjs
 * @description: 动态代理：分jdk和cglib两种代理,本次实现jdk代理,
 *   java.lang.reflect 包中的 Proxy 类和InvocationHandler 接口提供了生成动态代理类的能力。
 * @date: 2022/8/9 16:37
 **/
@Service
public class DynamicProxyServiceImpl implements DynamicProxyService {

    @Override
    public void jdkDynamicProxy() {
        WolfWarriorMovie wolfWarriorMovie = new WolfWarriorMovie();
        MovieInvocationHandler invocationHandler = new MovieInvocationHandler(wolfWarriorMovie);
        Movie movie = (Movie)Proxy.newProxyInstance(WolfWarriorMovie.class.getClassLoader(),
                WolfWarriorMovie.class.getInterfaces(),invocationHandler);
        movie.play();
    }

    @Override
    public void cglibDynamicProxy() {
        //指定目录下生成动态代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"D:\\class");
        //创建Enhancer对象，类似JDK动态代理的Proxy类
        Enhancer enhancer = new Enhancer();
        //设置代理目标类
        enhancer.setSuperclass(WolfWarriorMovie.class);
        //设置单一回调对象，在调用中拦截堆目标方法的调用(自定义的MethodInterceptor类)
        enhancer.setCallback(new CglibDynamicProxyInterceptor());
        //正式创建代理类
        WolfWarriorMovie wolfWarriorMovie = (WolfWarriorMovie)enhancer.create();
        //执行方法
        wolfWarriorMovie.play();

    }
}