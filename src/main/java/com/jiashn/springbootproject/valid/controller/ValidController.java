package com.jiashn.springbootproject.valid.controller;

import com.jiashn.springbootproject.utils.ResultUtil;
import com.jiashn.springbootproject.valid.domain.Employee;
import com.jiashn.springbootproject.valid.domain.UserInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: jiangjs
 * @description: 实现各参数校验
 * @date: 2022/6/21 10:34
 **/
@RestController
@RequestMapping("/valid")
public class ValidController {

    /**
     * 路径上进行参数校验：参数:正则表达式，不方便：无法自定义格式响应参数，不能捕捉异统一返回前端
     */
    @GetMapping("/getUserInfoByUserId.do/{userId:[0-9_]+}")
    public String getUserInfoByUserId(@PathVariable("userId") Integer userId){
        return String.valueOf(userId);
    }

    /**
     * 分组校验数据信息：定义Default
     */
    @PostMapping("/insertUserInfo.do")
    public ResultUtil<?> insertUserInfo(@Validated(value = UserInfo.Default.class) @RequestBody UserInfo userInfo){
        return ResultUtil.success(userInfo.toString());
    }

    /**
     * 分组校验数据信息：定义Default，Update
     */
    @PostMapping("/updateUserInfo.do")
    public ResultUtil<?> updateUserInfo(@Validated(value = {UserInfo.Update.class,UserInfo.Default.class}) @RequestBody UserInfo userInfo){
        return ResultUtil.success(userInfo.toString());
    }

    /**
     * 自定义校验数据信息
     */
    @PostMapping("/definedEmployeeInfo.do")
    public ResultUtil<?> definedEmployeeInfo(@Validated @RequestBody Employee employee){
        return ResultUtil.success(employee.toString());
    }
}