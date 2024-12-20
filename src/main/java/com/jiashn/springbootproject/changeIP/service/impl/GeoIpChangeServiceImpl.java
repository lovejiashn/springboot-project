package com.jiashn.springbootproject.changeIP.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jiashn.springbootproject.changeIP.service.GeoIpChangeService;
import com.jiashn.springbootproject.changeIP.utils.GeoLiteUtil;
import com.jiashn.springbootproject.utils.GetUserIP;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.StringJoiner;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/5/23 14:53
 **/
@Service
public class GeoIpChangeServiceImpl implements GeoIpChangeService {

    public static final Logger log = LoggerFactory.getLogger(GeoIpChangeServiceImpl.class);

    @Override
    public ResultUtil<JSONObject> getIpAddress(String ip,HttpServletRequest request) {
        try {
            return ResultUtil.success(GeoLiteUtil.getIpToAddress(ip));
        }catch (Exception e){
            e.printStackTrace();
            log.error("ip转换国家、省、城市、经纬度报错：{}",e.getMessage());
        }
        return ResultUtil.error("获取ip对应地址等信息报错");
    }

    @Override
    public ResultUtil<JSONObject> getLocationIpAddress(HttpServletRequest request) {
        return null;
    }
}