package com.jiashn.springbootproject.changeIP.service;

import com.alibaba.fastjson.JSONObject;
import com.jiashn.springbootproject.utils.ResultUtil;
import netscape.javascript.JSObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/5/23 14:52
 **/
public interface GeoIpChangeService {

    /**
     * 获取当前IP所在的地址（国，省，市）
     * @param ip ip地址
     * @param request 请求
     * @return 地址
     */
    ResultUtil<JSONObject> getIpAddress(String ip, HttpServletRequest request);
}
