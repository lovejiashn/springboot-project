package com.jiashn.springbootproject.desensitize.config;

import com.jiashn.springbootproject.desensitize.plugin.DesensitizedPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/4/15 16:46
 **/
@Configuration
public class DesensitizedPluginConfig {

    @Bean
    public DesensitizedPlugin desensitizedPlugin(){
        return new DesensitizedPlugin();
    }
}