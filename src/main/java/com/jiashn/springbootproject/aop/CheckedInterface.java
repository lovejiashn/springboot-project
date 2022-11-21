package com.jiashn.springbootproject.aop;

import java.lang.annotation.*;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/10/13 11:22
 **/
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckedInterface {
}
