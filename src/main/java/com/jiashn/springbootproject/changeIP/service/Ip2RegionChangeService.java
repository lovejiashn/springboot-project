package com.jiashn.springbootproject.changeIP.service;

import com.jiashn.springbootproject.utils.ResultUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/7/28 14:30
 **/
public interface Ip2RegionChangeService {

    /**
     * 将ip转换成地址
     * @param ip 需转换Ip
     * @return 返回结果
     */
    ResultUtil<String> getIpToAddress(String ip, HttpServletRequest request);
}
