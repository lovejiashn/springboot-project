package com.jiashn.springbootproject.cache.controller;

import com.jiashn.springbootproject.cache.domain.OpuOmUser;
import com.jiashn.springbootproject.cache.service.OpuOmUserService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jiangjs
 * @description: org.springframework.cache 依赖于redis，且返回数据需要序列化
 * @date: 2022/8/23 14:25
 **/
@RestController
@RequestMapping("/user")
public class OpuOmUserController {

    @Resource
    private OpuOmUserService opuOmUserService;

    @GetMapping("/getUserInfoByUserId.do/{userId}")
    public ResultUtil<OpuOmUser> getUserInfoByUserId(@PathVariable("userId") String userId){
        return opuOmUserService.getUserInfoByUserId(userId);
    }

    @GetMapping("/updateUserInfoByUserId.do/{userId}")
    public ResultUtil<OpuOmUser> updateUserInfoByUserId(@PathVariable("userId") String userId){
        return opuOmUserService.updateUserInfoByUserId(userId);
    }

    @GetMapping("/deleteUserInfoByUserId.do/{userId}")
    public ResultUtil<OpuOmUser> deleteUserInfoByUserId(@PathVariable("userId") String userId){
        return opuOmUserService.deleteUserInfoByUserId(userId);
    }
}