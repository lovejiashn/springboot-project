package com.jiashn.springbootproject.changeIP.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jiashn.springbootproject.changeIP.service.Ip138ChangeService;
import com.jiashn.springbootproject.changeIP.utils.Ip138ChangeUtil;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/8 17:26
 **/
@Service
public class Ip138ChangeServiceImpl implements Ip138ChangeService {

    private static final Logger log = LoggerFactory.getLogger(Ip138ChangeServiceImpl.class);
    @Override
    public ResultUtil<JSONObject> getIpToAddress(String ip) {
        try {
            return ResultUtil.success(Ip138ChangeUtil.getIpToAddress(ip));
        } catch (IOException e) {
            log.error("获取ip对应地址报错："+e.getMessage());
            e.printStackTrace();
            return ResultUtil.error("查询失败");
        }
    }
}