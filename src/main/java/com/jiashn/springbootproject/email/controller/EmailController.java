package com.jiashn.springbootproject.email.controller;

import com.alibaba.fastjson.JSONArray;
import com.jiashn.springbootproject.email.entity.EmailInfo;
import com.jiashn.springbootproject.email.entity.ReqEmail;
import com.jiashn.springbootproject.email.service.EmailService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: jiangjs
 * @Description: 发送邮箱信息
 * @Date: 2021/11/15 17:01
 **/
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail.do")
    public ResultUtil<?> sendEmail(ReqEmail reqEmail){
        return emailService.sendEmail(reqEmail);
    }

    @GetMapping("/sendEmailInfo.do")
    public ResultUtil<?> sendEmailInfo(){
        return emailService.sendEmailInfo();
    }

    @PostMapping("/recipientEmail.do")
    public ResultUtil<JSONArray> recipientEmail(@RequestBody EmailInfo emailInfo){
        return emailService.recipientEmail(emailInfo);
    }
}