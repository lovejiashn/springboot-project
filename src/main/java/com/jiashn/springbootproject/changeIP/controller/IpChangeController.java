package com.jiashn.springbootproject.changeIP.controller;

import com.alibaba.fastjson.JSONObject;
import com.jiashn.springbootproject.changeIP.service.IpChangeService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: jiangjs
 * @description: 将IP转换成国家、省、市，以及对应的经纬度
 * @date: 2022/5/23 14:44
 **/
@RestController
@RequestMapping("/change/ip")
public class IpChangeController {

    @Resource
    private IpChangeService ipChangeService;
    @GetMapping("/getIpAddress.do/{ip}")
    public ResultUtil<JSONObject> getIpAddress(@PathVariable("ip") String ip, HttpServletRequest request){
        return ipChangeService.getIpAddress(ip,request);
    }
}