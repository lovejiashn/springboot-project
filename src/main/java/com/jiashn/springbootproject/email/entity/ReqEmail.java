package com.jiashn.springbootproject.email.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Properties;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/15 14:56
 **/
@Data
public class ReqEmail extends EmailInfo {
    /**
     * 发送者
     */
    private String sender;
    /**
     * 收件人
     */
    private String recipient;
    /**
     * 抄送人
     */
    private String ccPerson;
    /**
     * 邮件内容
     */
    private String content;
    /**
     * 密送人邮箱地址（多个以逗号隔开）
     */
    private String bccAddress;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件附件
     */
    private MultipartFile[] attachFiles;

}