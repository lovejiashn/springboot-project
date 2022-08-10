package com.jiashn.springbootproject.proxy.service.impl;

import com.jiashn.springbootproject.proxy.service.StaticProxyService;
import org.springframework.stereotype.Service;

/**
 * @author: jiangjs
 * @description: 静态代理：
 * 优点
 * 代理模式在客户端与目标对象之间起到一个中介作用和保护目标对象的作用
 *
 * 代理对象可以扩展目标对象的功能
 *
 * 代理模式能将客户端与目标对象分离，在一定程度上降低了系统的耦合度。
 *
 * 缺点
 * 代理对象需要与目标对象实现一样的接口,所以会有很多代理类,类太多.同时,一旦接口增加方法,目标对象与代理对象都要维护。
 * @date: 2022/8/9 16:06
 **/
@Service
public class StaticProxyServiceImpl implements StaticProxyService {

    @Override
    public void staticProxy() {
        YaoShenMovie yaoShenMovie = new YaoShenMovie();
        MovieProxy movieProxy = new MovieProxy(yaoShenMovie);
        movieProxy.play();
    }
}