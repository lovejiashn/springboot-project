package com.jiashn.springbootproject.email.utils;

import com.jiashn.springbootproject.email.entity.SendMailMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/6/16 15:33
 **/
@Component
public class EmailManageUtil {

    private static final Logger log = LoggerFactory.getLogger(EmailManageUtil.class);

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public boolean sendMail(SendMailMessage message){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,Boolean.TRUE);
            messageHelper.setSubject(message.getSubject());
            messageHelper.setText(message.getContext(),Boolean.TRUE);
            Map<String, File> attachmentMap = message.getAttachments();
            if (Objects.nonNull(attachmentMap) && attachmentMap.size() > 0){
                for (String k : attachmentMap.keySet()) {
                    messageHelper.addAttachment(k,attachmentMap.get(k));
                }
            }
            if (Objects.nonNull(message.getCcPerson()) && message.getCcPerson().length > 0){
                messageHelper.setCc(message.getCcPerson());
            }
            if (Objects.nonNull(message.getBccPerson()) && message.getBccPerson().length > 0){
                messageHelper.setBcc(message.getBccPerson());
            }
            if (Objects.nonNull(message.getSentTo()) && message.getSentTo().length > 0){
                messageHelper.setTo(message.getSentTo());
            }
            messageHelper.setFrom(message.getSender());
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("发送邮件报错：" + e.getMessage());
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
