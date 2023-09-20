package com.jiashn.springbootproject.changeIP.controller;

import com.jiashn.springbootproject.changeIP.service.Ip2RegionChangeService;
import com.jiashn.springbootproject.changeIP.utils.Ip2RegionUtil;
import com.jiashn.springbootproject.utils.GetUserIP;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: jiangjs
 * @description: 基于ip2region将ip转换成国家、省、市信息
 * @date: 2022/7/28 14:29
 **/
@RestController
@RequestMapping("/ip2region")
public class Ip2RegionChangeController {

    @Resource
    private Ip2RegionChangeService ip2RegionChangeService;
    @Autowired
    private Ip2RegionUtil regionUtil;

    @GetMapping("/getIpToAddress.do")
    public ResultUtil<String> getIpToAddressNoIp(HttpServletRequest request){
        String userIp = GetUserIP.getUserOuterIpAddress();
        return ResultUtil.success(regionUtil.changeIpToAddress(userIp));
    }
    @GetMapping("/getIpToAddress.do/{ip}")
    public ResultUtil<String> getIpToAddress(@PathVariable("ip") String ip){
       return ResultUtil.success(regionUtil.changeIpToAddress(ip));
    }
}