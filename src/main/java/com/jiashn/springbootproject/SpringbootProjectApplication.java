package com.jiashn.springbootproject;

import com.xmjg.common.apply.aop.EnableXmjgCommonApply;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author jiangjs
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(value = "com.**.mapper.**")
@EnableXmjgCommonApply
public class SpringbootProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootProjectApplication.class, args);
    }

}
