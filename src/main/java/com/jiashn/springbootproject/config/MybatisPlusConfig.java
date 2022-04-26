package com.jiashn.springbootproject.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jiangjs
 * @description: mybatis-plus配置文件
 * @date: 2022/4/15 11:16
 **/
@Configuration
@MapperScan(value = "com.**.mapper.**")
public class MybatisPlusConfig {

    /**
     * 配置分页3.2.0
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}