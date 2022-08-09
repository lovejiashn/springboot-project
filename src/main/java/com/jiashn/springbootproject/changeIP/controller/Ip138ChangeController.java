package com.jiashn.springbootproject.changeIP.controller;

import com.alibaba.fastjson.JSONObject;
import com.jiashn.springbootproject.changeIP.service.Ip138ChangeService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jiangjs
 * @description: 调用ip138接口，通过jsoup截取html文件获得地址
 * @date: 2022/8/8 17:18
 **/
@RestController
@RequestMapping("/ip138")
public class Ip138ChangeController {

    @Autowired
    private Ip138ChangeService ip138ChangeService;

    @GetMapping("/getIpToAddress.do/{ip}")
    public ResultUtil<JSONObject> getIpToAddress(@PathVariable("ip") String ip){
        return ip138ChangeService.getIpToAddress(ip);
    }
}