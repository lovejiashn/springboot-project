package com.jiashn.springbootproject.changeIP.controller;

import com.jiashn.springbootproject.changeIP.utils.PhoneToRegionUtil;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/11/30 11:35
 **/
@RestController
@RequestMapping("/phone")
public class PhoneToRegionController {

    @GetMapping("/getPhoneCarrierInfo.do")
    public ResultUtil<?> getPhoneCarrierInfo(){
        return ResultUtil.success(PhoneToRegionUtil.getPhoneCarrier("15647259140"));
    }

    @GetMapping("/getPhoneGeoInfo.do")
    public ResultUtil<?> getPhoneGeoInfo(){
        return ResultUtil.success(PhoneToRegionUtil.getRegionInfoByPhone("15647259140"));
    }
}
