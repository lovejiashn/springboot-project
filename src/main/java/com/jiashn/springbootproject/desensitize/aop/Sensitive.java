package com.jiashn.springbootproject.desensitize.aop;

import com.jiashn.springbootproject.desensitize.enums.DesensitizedStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 脱敏注解
 * @author jiangjs
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Sensitive {
    DesensitizedStrategy strategy();
}
