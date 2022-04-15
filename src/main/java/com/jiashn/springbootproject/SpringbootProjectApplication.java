package com.jiashn.springbootproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author jiangjs
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringbootProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootProjectApplication.class, args);
    }

}
