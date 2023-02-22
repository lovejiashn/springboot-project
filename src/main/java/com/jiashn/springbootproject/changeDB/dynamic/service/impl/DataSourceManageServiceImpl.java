package com.jiashn.springbootproject.changeDB.dynamic.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiashn.springbootproject.changeDB.dynamic.domain.DataSourceInfo;
import com.jiashn.springbootproject.changeDB.dynamic.mapper.DataSourceManageMapper;
import com.jiashn.springbootproject.changeDB.dynamic.service.DataSourceManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/2/22 15:30
 **/
@Service
public class DataSourceManageServiceImpl implements DataSourceManageService {
    @Autowired
    private DataSourceManageMapper manageMapper;

    @Override
    public void changeDataSource() {
        DynamicDataSourceContextHolder.push("master");
        Wrapper<DataSourceInfo> sourceWrapper = Wrappers.<DataSourceInfo>lambdaQuery().eq(DataSourceInfo::getStatus,1);
        List<DataSourceInfo> sources = manageMapper.selectList(sourceWrapper);
        System.out.println(sources);
        DynamicDataSourceContextHolder.poll();
    }
}
