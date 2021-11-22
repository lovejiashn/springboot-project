package com.jiashn.springbootproject.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: jiangjs
 * @Description: minio属性
 * @Date: 2021/11/18 11:07
 **/
@Component
@Data
public class MinioPropertiesConfig {

    /**
     * 端点
     */
    @Value("${minio.endpoint}")
    private String endpoint;
    /**
     * 用户名
     */
    @Value("${minio.accessKey}")
    private String accessKey;
    /**
     * 密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;
    /**
     * 桶名称
     */
    @Value("${minio.bucketName}")
    private String bucketName;
}