package com.jiashn.springbootproject.email.service;

import com.alibaba.fastjson.JSONArray;
import com.jiashn.springbootproject.email.entity.EmailInfo;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/15 17:08
 **/
public interface EmailService {
    /**
     * 获取邮件信息
     * @param emailInfo 提交参数
     * @return 返回值
     */
    ResultUtil<JSONArray> recipientEmail(EmailInfo emailInfo);
}
