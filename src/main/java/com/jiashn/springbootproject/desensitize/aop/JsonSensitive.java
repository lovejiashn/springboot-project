package com.jiashn.springbootproject.desensitize.aop;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiashn.springbootproject.desensitize.enums.DesensitizedStrategy;
import com.jiashn.springbootproject.desensitize.plugin.JacksonDataDesensitized;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: jiangjs
 * @description: 使用jackson进行数据脱敏
 * @date: 2023/6/1 15:50
 **/
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = JacksonDataDesensitized.class)
public @interface JsonSensitive {
    DesensitizedStrategy strategy();
}
