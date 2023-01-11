package com.jiashn.springbootproject.interceptor;

import cn.hutool.core.annotation.AnnotationUtil;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/12/30 10:36
 **/
@Component
public class RequestSubmitInterceptor implements HandlerInterceptor {

    @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HandlerMethod method = (HandlerMethod)handler;
        //获取注解在方法上
        RequestSubmit methodSubmit = AnnotationUtils.findAnnotation(method.getMethod(), RequestSubmit.class);
        //注解在类上面
        RequestSubmit classSubmit = AnnotationUtils.findAnnotation(method.getMethod().getDeclaringClass(), RequestSubmit.class);
        if (Objects.nonNull(methodSubmit) || Objects.nonNull(classSubmit)){
            String uri = request.getRequestURI();
        }
        return true;
    }
}