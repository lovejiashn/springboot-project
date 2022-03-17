package com.jiashn.springbootproject.spi.service.impl;

import com.jiashn.springbootproject.changeDB.DataSource;
import com.jiashn.springbootproject.changeDB.DataSourceType;
import com.jiashn.springbootproject.spi.service.UseSelService;
import com.xmjg.common.apply.metrics.service.XmjgMetricsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/21 17:22
 **/
@Service
public class UseSelServiceImpl implements UseSelService {

    @Autowired
    private XmjgMetricsInfoService xmjgMetricsInfoService;

    @Override
    @DataSource(value = DataSourceType.MYSQL)
    public Integer getTotalProjectNum(String startDate, String endDate, String regionCode) {
        return xmjgMetricsInfoService.getTotalProjectNum(startDate,endDate,regionCode);
    }
}