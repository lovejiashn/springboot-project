package com.jiashn.springbootproject.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jiangjs
 * @Description:  根据判断条件动态的加载该Bean到SpringIoC容器中
 * @Date: 2022/1/24 14:52
 **/
@Configuration
public class ConditionalConfig {

    @Bean
    @Conditional(WindowConditional.class)
    public DiffSystemLoadBeanService windowSystemLoadBean(){
       return new WindowLoadBeanServiceImpl();
    }

    @Bean
    @Conditional(LinuxConditional.class)
    public DiffSystemLoadBeanService linuxSystemLoadBean(){
        return new LinuxLoadBeanServiceImpl();
    }
}