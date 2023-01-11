package com.jiashn.springbootproject.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/12/30 10:54
 **/
@Configuration
public class RequestSubmitConfig implements WebMvcConfigurer {

    @Autowired
    private RequestSubmitInterceptor submitInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        String[] exPath = {"/error","/files/**"};
        registry.addInterceptor(submitInterceptor).excludePathPatterns(exPath);
    }
}