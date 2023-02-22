package com.jiashn.springbootproject.changeDB.selfdefine;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Author: jiangjs
 * @Description: AbstractRoutingDataSource:根据用户定义的规则选择当前的数据源，
 *               作用：在执行查询之前，设置使用的数据源，实现动态路由的数据源，
 *               在每次数据库查询操作前执行它的抽象方法determineCurrentLookupKey()，决定使用哪个数据源
 * @Date: 2022/1/19 13:56
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource (DataSource defaultDataSource, Map<Object,Object> dataSources) {
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(dataSources);
        //完成属性设置
        super.afterPropertiesSet();
    }

    /**
     * 获取与数据源相关的key
     * 此key是Map<String,DataSource> resolvedDataSources 中与数据源绑定的key值
     * 在通过determineTargetDataSource获取目标数据源时使用
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }
}