package com.jiashn.springbootproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 配置cache配置，添加后@Cacheable需要添加判断：unless ="#result == null"
 * @date: 2022/8/23 15:55
 **/
@Component
@EnableCaching
public class CacheConfig {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    public CacheManager cacheManager(){
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                //设置key为string
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getStringSerializer()))
                //设置Value为自动转json的object
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                //不缓存null
                .disableCachingNullValues()
                //设置保存时间1天
                .entryTtl(Duration.ofDays(1));
        return RedisCacheManager.RedisCacheManagerBuilder
                // Redis 连接工厂
                .fromConnectionFactory(Objects.requireNonNull(redisTemplate.getConnectionFactory()))
                // 缓存配置
                .cacheDefaults(cacheConfiguration)
                // 配置同步修改或删除 put/evict
                .transactionAware()
                .build();
    }
}