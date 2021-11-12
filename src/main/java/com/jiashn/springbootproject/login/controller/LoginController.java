package com.jiashn.springbootproject.login.controller;

import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/12 15:07
 **/
@RestController
@RequestMapping("/user/login")
public class LoginController {

    @PostMapping("/userNameAndPassWord.do")
    public ResultUtil loginByUserNameAndPassWord(){
        return null;
    }
}