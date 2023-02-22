package com.jiashn.springbootproject.changeDB.dynamic.util;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiashn.springbootproject.changeDB.dynamic.domain.DataSourceInfo;
import com.jiashn.springbootproject.changeDB.dynamic.mapper.DataSourceManageMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author: jiangjs
 * @description: 从数据库表中加载数据库信息并添加进入 DynamicRoutingDataSource 便于在方法中使用
 * @date: 2023/2/22 14:16
 **/
@Component
public class DynamicDataSourceLoad implements CommandLineRunner {
    @Autowired
    private DataSourceManageMapper manageMapper;
    /**
     * 核心动态数据源组件
     */
    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;
    /**
     * Druid数据源创建器
     */
    @Autowired
    private DruidDataSourceCreator dataSourceCreator;
    @Override
    public void run(String... args) throws Exception {
        Wrapper<DataSourceInfo> sourceWrapper = Wrappers.<DataSourceInfo>lambdaQuery().eq(DataSourceInfo::getStatus,1);
        List<DataSourceInfo> sources = manageMapper.selectList(sourceWrapper);
        if (CollectionUtils.isNotEmpty(sources)){
            sources.forEach(dataSourceInfo -> {
                DataSourceProperty property = new DataSourceProperty();
                property.setDriverClassName(dataSourceInfo.getDriver());
                property.setUsername(dataSourceInfo.getUserName());
                property.setPassword(dataSourceInfo.getPassword());
                property.setUrl(dataSourceInfo.getUrl());
                DataSource dataSource = dataSourceCreator.createDataSource(property);
                dynamicRoutingDataSource.addDataSource(dataSourceInfo.getDataSourceName(),dataSource);
            });
        }
    }
}
