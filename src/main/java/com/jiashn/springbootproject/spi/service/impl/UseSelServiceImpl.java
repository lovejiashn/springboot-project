package com.jiashn.springbootproject.spi.service.impl;

import com.jiashn.springbootproject.changeDB.selfdefine.DataSource;
import com.jiashn.springbootproject.changeDB.selfdefine.DataSourceType;
import com.jiashn.springbootproject.spi.service.UseSelService;
import org.springframework.stereotype.Service;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/21 17:22
 **/
@Service
public class UseSelServiceImpl implements UseSelService {



    @Override
    @DataSource(value = DataSourceType.MYSQL)
    public Integer getTotalProjectNum(String startDate, String endDate, String regionCode) {
        return 1;
    }
}