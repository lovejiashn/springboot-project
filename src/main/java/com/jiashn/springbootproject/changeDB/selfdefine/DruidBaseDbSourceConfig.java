package com.jiashn.springbootproject.changeDB.selfdefine;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/19 12:29
 **/
@Configuration
public class DruidBaseDbSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.mysql")
    public DataSource mysqlDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.clickhouse")
    public DataSource clickHouseDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dynamicDataSource(){
        Map<Object, Object> sourceMap = new HashMap<>(2);
        DataSource defaultDataSource = mysqlDataSource();
        sourceMap.put(DataSourceType.MYSQL.name(),defaultDataSource);
        sourceMap.put(DataSourceType.CLICKHOUSE.name(),clickHouseDataSource());
        return new DynamicDataSource(defaultDataSource,sourceMap);
    }
}