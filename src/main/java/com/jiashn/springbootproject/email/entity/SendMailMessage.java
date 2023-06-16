package com.jiashn.springbootproject.email.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/6/16 16:06
 **/
@Data
@Accessors(chain = true)
public class SendMailMessage {
    /**
     * 发送者
     */
    @Value("${spring.mail.username}")
    private String sender;
    /**
     * 接收者
     */
    private String[] sentTo;

    /**
     * 抄送者
     */
    private String[] ccPerson;
    /**
     * 密送人
     */
    private String[] bccPerson;

    /**
     * 主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String context;

    /**
     * 附件
     */
    private Map<String,File> attachments;

}
