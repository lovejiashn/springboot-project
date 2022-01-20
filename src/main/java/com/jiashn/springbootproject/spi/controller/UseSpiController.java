package com.jiashn.springbootproject.spi.controller;

import com.common.apply.xmjg.metrics.service.XmjgMetricsInfoService;
import com.jiashn.springbootproject.changeDB.DataSource;
import com.jiashn.springbootproject.changeDB.DataSourceType;
import com.jiashn.springbootproject.spi.mapper.ChinaSelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/19 10:36
 **/
@RestController
@RequestMapping("/test")
public class UseSpiController {

    @Autowired
    private XmjgMetricsInfoService xmjgMetricsInfoService;

    @Autowired
    private ChinaSelMapper chinaSelMapper;

    @GetMapping("/getTotalProjectNum.do")
    @DataSource(value = DataSourceType.CLICKHOUSE)
    public void getTotalProjectNum(){
        /*ServiceLoader<XmjgMetricsInfoService> service = ServiceLoader.load(XmjgMetricsInfoService.class);
        for (XmjgMetricsInfoService xmjgMetricsInfoService : service) {
            Integer totalProjectNum = xmjgMetricsInfoService.getTotalProjectNum("2021-01-01", "2022-01-01", "");
            System.out.println("总项目数：" + totalProjectNum);
        }*/
        Integer totalProjectNum = xmjgMetricsInfoService.getTotalProjectNum("2021-01-01", "2022-01-01", "");
        Integer piecesNum = xmjgMetricsInfoService.getTotalPiecesNum("2021-01-01", "2022-01-01", "");
        System.out.println("总项目数：" + totalProjectNum+",总办件数："+piecesNum);
       /* Wrapper<ChinaEntity> chinaEntityWrapper = Wrappers.<ChinaEntity>lambdaQuery()
                .eq(ChinaEntity::getCode,110000);
        ChinaEntity chinaEntity = chinaSelMapper.selectOne(chinaEntityWrapper);
        System.out.println("查询结构："+chinaEntity.toString());*/
    }
}