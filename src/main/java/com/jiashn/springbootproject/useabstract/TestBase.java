package com.jiashn.springbootproject.useabstract;

import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/8/27 15:56
 **/
@Component
public class TestBase {
    @Transactional
    public void test(){
        System.out.println("test");
    }
}
