package com.jiashn.springbootproject.changeIP.service;

import com.alibaba.fastjson.JSONObject;
import com.jiashn.springbootproject.utils.ResultUtil;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/8 17:21
 **/
public interface Ip138ChangeService {
    /**
     * 调用138接口抓取页面上对应的地址
     * @param ip 查询ip
     * @return 返回值
     */
    ResultUtil<JSONObject> getIpToAddress(String ip);
}
