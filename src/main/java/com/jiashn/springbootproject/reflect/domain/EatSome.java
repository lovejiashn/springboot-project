package com.jiashn.springbootproject.reflect.domain;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/6/30 10:42
 **/
public class EatSome implements DoSomeThing{
    @Override
    public String eat() {
        return "狗粮";
    }
}
