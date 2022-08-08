package com.jiashn.springbootproject.changeIP.controller;

import com.jiashn.springbootproject.changeIP.service.Ip2RegionChangeService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @GetMapping("/getIpToAdress.do/{ip}")
    public ResultUtil<String> getIpToAdress(@PathVariable("ip") String ip){
       return ip2RegionChangeService.getIpToAdress(ip);
    }
}