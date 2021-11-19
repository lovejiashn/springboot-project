package com.jiashn.springbootproject.config;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/18 11:09
 **/
@Configuration
@EnableConfigurationProperties(MinioPropertiesConfig.class)
public class MinioConfig {

    @Resource
    private MinioPropertiesConfig propertiesConfig;

    /**
     * 初始化Minio客户端
     * @return 返回初始化结果
     */
    @Bean
    public MinioClient minioClient(){
        MinioClient minioClient = MinioClient.builder()
                .endpoint(propertiesConfig.getEndpoint())
                .credentials(propertiesConfig.getAccessKey(),propertiesConfig.getSecretKey())
                .build();
        return minioClient;
    }
}