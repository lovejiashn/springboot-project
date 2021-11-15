package com.jiashn.springbootproject.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiashn.springbootproject.email.entity.EmailInfo;
import com.jiashn.springbootproject.email.entity.ReqEmail;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: jiangjs
 * @Description: 邮件工具类
 * @Date: 2021/11/15 14:53
 **/
public class EmailUtil {
    private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);

    public static boolean sendEmail(ReqEmail reqEmail){
        //
        try {
            Message message = getMessage(reqEmail);
            //设置消息的主要内容
            message.setText(reqEmail.getContent());
            message.saveChanges();
            //发送消息
            Transport.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            log.error("发送邮件信息报错：{}",e.getMessage());
            return false;
        }
    }

    /**
     * 获取邮件消息
     * @param reqEmail 发送消息实体
     * @return 返回消息
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private static Message getMessage(ReqEmail reqEmail) throws MessagingException, UnsupportedEncodingException {
        //设置session
        Session session = Session.getDefaultInstance(reqEmail.getProperties(), reqEmail.getAuthenticator());
        //开启debug模式，查看邮件运行状态
        session.setDebug(Boolean.TRUE);
        //根据session创建一个邮件消息
        Message message = new MimeMessage(session);
        //设置消息发送者
        message.setFrom(new InternetAddress(reqEmail.getSender()));
        //设置消息接收者
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(reqEmail.getRecipient()));
        //设置抄送人
        if (StringUtils.isNotBlank(reqEmail.getCcPerson())){
            message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(reqEmail.getCcPerson()));
        }
        //设置密送
        if (StringUtils.isNotBlank(reqEmail.getBccAddress())){
            message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(reqEmail.getBccAddress()));
        }
        //设置消息主题
        message.setSubject(MimeUtility.encodeText(reqEmail.getSubject(),"utf-8","B"));
        //设置消息发送时间
        message.setSentDate(new Date());
        return message;
    }

    /**
     * 获取收件信息
     * @param emailInfo 提交邮件参数
     * @return 返回收件数据信息
     */
    public static JSONArray recipientEmail(EmailInfo emailInfo){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        Session session = Session.getDefaultInstance(emailInfo.getProperties(), emailInfo.getAuthenticator());
        session.setDebug(Boolean.TRUE);
        JSONArray receivedArray = new JSONArray();
        Store store = null;
        Folder folder = null;
        try {
            store = session.getStore("pop3");
            store.connect(emailInfo.getUserName(),emailInfo.getPassword());
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();
            for (Message message : messages) {
                JSONObject resJson = new JSONObject();
                resJson.put("emailNum",message.getMessageNumber());
                resJson.put("topic",MimeUtility.decodeText(message.getSubject()));
                resJson.put("sender",InternetAddress.toString(message.getFrom()));
                resJson.put("content",message.getContent());
                resJson.put("receivedDate",dateFormat.format(message.getReceivedDate()));
                resJson.put("sentDate",dateFormat.format(message.getSentDate()));
                resJson.put("isRead",message.getFlags().contains(Flags.Flag.SEEN));
                receivedArray.add(resJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取收件信息报错：{}",e.getMessage());
        } finally {
            if (Objects.nonNull(folder)){
                try {
                    folder.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            if (Objects.nonNull(store)){
                try {
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
        return receivedArray;
    }
}