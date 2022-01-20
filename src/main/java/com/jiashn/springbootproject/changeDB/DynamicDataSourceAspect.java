package com.jiashn.springbootproject.changeDB;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/19 14:53
 **/
@Aspect
@Component
@Order(1)
@Slf4j
public class DynamicDataSourceAspect {

    /**
     * 所有方法都可以加
     */
    @Pointcut("@annotation(com.jiashn.springbootproject.changeDB.DataSource)"
            + "|| @within(com.jiashn.springbootproject.changeDB.DataSource)")
    public void dataSourcePointcut(){
    }

    @Around("dataSourcePointcut()")
    public Object dataSourceAround(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        DataSource annotation = method.getAnnotation(DataSource.class);
        if (Objects.nonNull(annotation)){
            DataSourceContextHolder.setDataSourceType(annotation.value().name());
        }
        try {
            return point.proceed();
        }finally {
            DataSourceContextHolder.removeDataSourceType();
        }
    }
}