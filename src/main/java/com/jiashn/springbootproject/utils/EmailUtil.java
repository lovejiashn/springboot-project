package com.jiashn.springbootproject.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiashn.springbootproject.email.entity.EmailInfo;
import com.jiashn.springbootproject.email.entity.ReqEmail;
import com.sun.mail.imap.IMAPStore;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: jiangjs
 * @Description: 邮件工具类
 * @Date: 2021/11/15 14:53
 **/
public class EmailUtil {
    private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);

    private final static String uploadPath = "D:/xmjg/file";

    public static boolean sendEmail(ReqEmail reqEmail){
        Transport transport = null;
        try {
            //设置session
            Session session = Session.getDefaultInstance(reqEmail.getSendProperties(), reqEmail.getAuthenticator());
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
            //设置邮件内容信息
            Multipart multipart = new MimeMultipart();
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(reqEmail.getContent());
            multipart.addBodyPart(contentPart);
            //设置附件信息
            MultipartFile[] attachFiles = reqEmail.getAttachFiles();
            if (attachFiles.length > 0){
                for (MultipartFile attachFile : attachFiles) {
                    String fileName = attachFile.getOriginalFilename();
                    //先上传文件
                    File file = new File(uploadPath + "/email/"+fileName);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    attachFile.transferTo(file);
                    MimeBodyPart attach = new MimeBodyPart();
                    DataHandler dataHandler = new DataHandler(new FileDataSource(file));
                    attach.setDataHandler(dataHandler);
                    attach.setFileName(MimeUtility.encodeText(dataHandler.getName()));
                    multipart.addBodyPart(attach);
                }
            }
            message.setContent(multipart);
            //设置消息发送时间
            message.setSentDate(new Date());
            transport = session.getTransport();
            transport.connect(reqEmail.getMailServerHost(),reqEmail.getUserName(),reqEmail.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            log.error("发送邮件信息报错：{}",e.getMessage());
            return false;
        }finally {
            if (Objects.nonNull(transport)){
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取收件信息
     * @param emailInfo 提交邮件参数
     * @return 返回收件数据信息
     */
    public static JSONArray recipientEmail(EmailInfo emailInfo){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //这部分就是解决异常的关键所在，设置IAMP ID信息
        Map<String,String> iamMap = new HashMap<>(4);
        //带上IMAP ID信息，由key和value组成,value随便填写
        iamMap.put("name","myname");
        iamMap.put("version","1.0.0");
        iamMap.put("vendor","myclient");
        iamMap.put("support-email","testmail@test.com");
        Session session = Session.getDefaultInstance(emailInfo.getProperties(), emailInfo.getAuthenticator());
        session.setDebug(Boolean.TRUE);
        JSONArray receivedArray = new JSONArray();
        IMAPStore store = null;
        Folder folder = null;
        try {
            store = (IMAPStore)session.getStore("imap");
            store.connect(emailInfo.getUserName(),emailInfo.getPassword());
            store.id(iamMap);
            folder = store.getFolder(emailInfo.getType());
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();
            for (Message message : messages) {
                JSONObject resJson = new JSONObject();
                resJson.put("emailNum",message.getMessageNumber());
                resJson.put("topic",MimeUtility.decodeText(message.getSubject()));
                resJson.put("sender",getReceiverOrSenders(message.getReplyTo()));
                resJson.put("receiver",getReceiverOrSenders(message.getAllRecipients()));
                StringBuilder content = new StringBuilder();
                getMailTextContent(message,content,emailInfo.getType());
                resJson.put("content",content);
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

    /**
     * 获得邮件文本内容
     * @param part 邮件体
     * @param content 存储邮件文本内容的字符串
     * @throws MessagingException 信息异常
     * @throws IOException IO异常
     */
    public static void getMailTextContent(Part part, StringBuilder content,String type) throws MessagingException, IOException {
        //如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part)part.getContent(),content,type);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = Math.min(multipart.getCount(), 1);
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart,content,type);
            }
        }
    }

    private static String getReceiverOrSenders(Address[] addresses){
        StringJoiner receiver = new StringJoiner(",");
        for (Address address : addresses) {
            InternetAddress internetAddress = (InternetAddress) address;
            receiver.add(internetAddress.getAddress());
        }
        return String.valueOf(receiver);
    }

}