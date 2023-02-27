package com.jiashn.springbootproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.IOException;

/**
 * @author jiangjs
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringbootProjectApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringbootProjectApplication.class, args);
    }

}
