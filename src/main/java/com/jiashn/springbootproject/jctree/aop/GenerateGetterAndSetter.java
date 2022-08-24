package com.jiashn.springbootproject.jctree.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jiangjs
 * 使用JCTree在编译时生产成本后续代码，例如get\set方法
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface GenerateGetterAndSetter {
}
