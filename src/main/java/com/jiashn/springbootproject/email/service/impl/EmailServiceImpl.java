package com.jiashn.springbootproject.email.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.jiashn.springbootproject.email.entity.EmailInfo;
import com.jiashn.springbootproject.email.entity.ReqEmail;
import com.jiashn.springbootproject.email.service.EmailService;
import com.jiashn.springbootproject.utils.EmailUtil;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.stereotype.Service;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/15 17:09
 **/
@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public ResultUtil<?> sendEmail(ReqEmail reqEmail) {
        boolean email = EmailUtil.sendEmail(reqEmail);
        return email ? ResultUtil.success("发送邮件成功") : ResultUtil.error("发送邮件失败");
    }

    @Override
    public ResultUtil<JSONArray> recipientEmail(EmailInfo emailInfo) {
        JSONArray jsonArray = EmailUtil.recipientEmail(emailInfo);
        return ResultUtil.success(jsonArray);
    }
}