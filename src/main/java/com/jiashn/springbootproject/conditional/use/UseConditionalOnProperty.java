package com.jiashn.springbootproject.conditional.use;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjs
 * @description: 使用@ConditionalOnProperty
 * @date: 2023/4/24 10:20
 **/
@Component
@Slf4j
public class UseConditionalOnProperty {

    @Value("${is_load_bean}")
    private String isLoadBean;

    @Bean
    @ConditionalOnProperty(value = "is_load_bean",havingValue = "true")
    public void loadBean(){
        log.info("是否加载当前类");
    }

    @Bean
    public void compareLoadBean(){
        log.info("加载bean属性：" + isLoadBean);
    }
}
