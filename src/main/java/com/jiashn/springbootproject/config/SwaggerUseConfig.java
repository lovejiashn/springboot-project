package com.jiashn.springbootproject.config;

import org.checkerframework.checker.units.qual.A;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/18 11:05
 **/
@Configuration
@EnableSwagger2
public class SwaggerUseConfig {

    @Bean
    public Docket createSwaggerRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("测试一下")
                .apiInfo(new ApiInfoBuilder()
                        .title("测试swagger")
                         .description("测试swagger")
                        .termsOfServiceUrl("").version("1.0")
                        .build()
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jiashn.springbootproject"))
                .paths(PathSelectors.any())
                .build();
    }
}