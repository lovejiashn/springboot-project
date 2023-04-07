package com.jiashn.springbootproject.changeDB.dynamic.util;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiashn.springbootproject.changeDB.dynamic.domain.DataSourceInfo;
import com.jiashn.springbootproject.changeDB.dynamic.mapper.DataSourceManageMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author: jiangjs
 * @description: 从数据库表中加载数据库信息并添加进入 DynamicRoutingDataSource 便于在方法中使用
 * @date: 2023/2/22 14:16
 **/
public class DynamicDataSourceLoad implements CommandLineRunner {
    @Autowired
    private DataSourceManageMapper manageMapper;

    /**
     * Druid数据源创建器
     */
    @Autowired
    private DefaultDataSourceCreator dataSourceCreator;

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        Wrapper<DataSourceInfo> sourceWrapper = Wrappers.<DataSourceInfo>lambdaQuery().eq(DataSourceInfo::getStatus,1);
        List<DataSourceInfo> sources = null; //manageMapper.selectList(sourceWrapper);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        if (CollectionUtils.isNotEmpty(sources)){
            sources.forEach(dataSourceInfo -> {
                DataSourceProperty property = new DataSourceProperty();
                property.setDriverClassName(dataSourceInfo.getDriver());
                property.setUsername(dataSourceInfo.getUserName());
                property.setPassword(dataSourceInfo.getPassword());
                property.setUrl(dataSourceInfo.getUrl());
                //设置连接异常时的重试次数
                property.getDruid().setConnectionErrorRetryAttempts(3);
                property.getDruid().setBreakAfterAcquireFailure(true);
                DataSource dataSource = dataSourceCreator.createDataSource(property);
                ds.addDataSource(dataSourceInfo.getDataSourceName(),dataSource);
            });
        }
    }
}
