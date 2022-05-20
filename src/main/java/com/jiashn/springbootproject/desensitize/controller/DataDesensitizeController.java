package com.jiashn.springbootproject.desensitize.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiashn.springbootproject.desensitize.domain.UserInfo;
import com.jiashn.springbootproject.desensitize.service.UserInfoService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: jiangjs
 * @description: 实现敏感信息脱敏功能，例如：手机号，姓名，身份证
 * @date: 2022/4/15 11:10
 **/
@RestController
@RequestMapping("/desensitize")
public class DataDesensitizeController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/getUserInfoPage.do")
    public ResultUtil<IPage<UserInfo>> getUserInfoPage(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,
                                                      @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        return userInfoService.getUserInfoPage(pageNo, pageSize);
    }
}