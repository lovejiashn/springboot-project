package com.jiashn.springbootproject.useUtil;

import org.springframework.util.Assert;

import java.util.ArrayList;

/**
 * @author: jiangjs
 * @description: 使用Assert
 * @date: 2022/7/12 14:04
 **/
public class UseAssert {

    public static void main(String[] args) {
       // Assert.notNull(null,"参数不能为空");

        Assert.notEmpty(new ArrayList<>(),"集合不能为空");

    }
}