package com.jiashn.springbootproject.proxy.controller;

import com.jiashn.springbootproject.proxy.service.DynamicProxyService;
import com.jiashn.springbootproject.proxy.service.StaticProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jiangjs
 * @description: 代理，作用即再不改变原来程序的基础上添加其他功能
 * @date: 2022/8/9 15:57
 **/
@RestController
@RequestMapping("/proxy")
public class ProxyController {

    @Autowired
    private StaticProxyService staticProxyService;
    @Autowired
    private DynamicProxyService dynamicProxyService;
    @GetMapping("/static.do")
    public void staticProxy(){
        staticProxyService.staticProxy();
    }

    @GetMapping("/jdk/dynamic.do")
    public void jdkDynamicProxy(){
        dynamicProxyService.jdkDynamicProxy();
    }

    @GetMapping("/cglib/dynamic.do")
    public void cglibDynamicProxy(){
        dynamicProxyService.cglibDynamicProxy();
    }
}