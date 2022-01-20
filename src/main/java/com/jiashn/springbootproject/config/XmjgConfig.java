package com.jiashn.springbootproject.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/19 10:55
 **/
@Configuration
@ComponentScan({"com.common.apply.xmjg.**","com.common.apply.common.**"})
public class XmjgConfig {
}