package com.jiashn.springbootproject.proxy.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: jiangjs
 * @description:
 * CGlib 是一个强大的,高性能,高质量的 Code 生成类库。它可以在运行期扩展 Java 类与实现 Java 接口。
 * 用 CGlib 生成代理类是目标类的子类。
 *  用 CGlib 生成 代理类不需要接口。
 * 用 CGLib 生成的代理类重写了父类的各个方法。
 * 拦截器中的 intercept 方法内容正好就是代理类中的方法体
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