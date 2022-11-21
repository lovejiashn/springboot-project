package com.jiashn.springbootproject.aop;

import com.aspose.slides.Collections.ArrayList;
import com.jiashn.springbootproject.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/10/13 11:32
 **/
@Aspect
@Component
@Slf4j
public class CheckedInterfaceAspect {

    @Pointcut("@annotation(com.jiashn.springbootproject.aop.CheckedInterface)")
    public void checked(){}

    @Around("checked()")
    public Object checkedReqData(ProceedingJoinPoint point) throws Throwable {
        /*Object obj = point.getArgs()[0];
        Field[] allField = getAllField(obj);
        if (allField.length <= 0){
            return ResultUtil.error("没有传递参数");
        }*/
        //获取请求头数据
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)attributes;
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        request.getSession();
        return point.proceed();
    }

    private Field[] getAllField(Object object){
        Class<?> clazz = object.getClass();
       /* List<Field> fieldList = new ArrayList<>();
        while (Objects.nonNull(clazz)){
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;*/
        return null;
    }
}