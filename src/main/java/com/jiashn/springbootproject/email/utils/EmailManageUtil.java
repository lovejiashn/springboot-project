package com.jiashn.springbootproject.email.utils;

import com.aspose.slides.internal.k4.art;
import com.jiashn.springbootproject.email.entity.ReceiveEmail;
import com.jiashn.springbootproject.email.entity.ReceiveMailMessage;
import com.jiashn.springbootproject.email.entity.SendMailMessage;
import com.jiashn.springbootproject.email.enums.EmailProtocol;
import com.jiashn.springbootproject.email.enums.ProtocolData;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    public ReceiveMailMessage receiveEmail(ReceiveEmail email){
        ReceiveMailMessage message = new ReceiveMailMessage();
        //接受者账号
        String receiverEmail = email.getReceiverEmail();
        String password = email.getReceiverPassword();
        EmailProtocol protocol = email.getProtocol();
        Map<String, String> emailMap = protocol.getProtocolData().get();
        String imap = emailMap.get("imap");
        Properties props = new Properties();
        props.put("mail.imap.host",imap);
        props.put("mail.imap.auth","true");
        props.setProperty("mail.store.protocol", "imap");
        props.put("mail.imap.starttls.enable", "true");
        Session session = Session.getInstance(props);
        try {
            Store store = session.getStore();
            store.connect(receiverEmail,password);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            message.setTotal(folder.getMessageCount()).setNewNum(folder.getNewMessageCount())
                    .setUnReadNum(folder.getUnreadMessageCount()).setDeletedNum(folder.getDeletedMessageCount());
            Message[] messages = folder.getMessages();
            for (Message msg : messages) {
                ReceiveMailMessage.EmailContent emailContent = new ReceiveMailMessage.EmailContent();
                Address[] bbcRecipients = msg.getRecipients(Message.RecipientType.BCC);
                Address[] ccRecipients = msg.getRecipients(Message.RecipientType.CC);
                Address[] toRecipients = msg.getRecipients(Message.RecipientType.TO);
                //获取邮件状态
               String status = msg.getFlags().contains(Flags.Flag.SEEN) ? "READ" : "UNREAD";
               StringBuffer sb = new StringBuffer();
                getEmailContent(msg,sb);
                emailContent.setSubject(msg.getSubject()).setSender(((InternetAddress) (msg.getFrom()[0])).getAddress())
                        .setSentDate(msg.getSentDate()).setBccPerson(this.getRecipients(bbcRecipients)).setCcPerson(this.getRecipients(ccRecipients))
                        .setSentTo(this.getRecipients(toRecipients)).setStatus(status).setContent(sb.toString())
                        .setAttachments(null);
            }
            folder.close();
            store.close();
        } catch (Exception e) {
            log.error("接收邮件报错：" + e.getMessage());
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 获取收件人
     * @param recipients 收件人信息
     * @return 返回收件人
     */
    private String getRecipients(Address[] recipients){
        return Arrays.stream(recipients).map(address -> ((InternetAddress) address).getAddress()).collect(Collectors.joining(","));
    }

    /**
     * 获取文件正文内容
     * @param msg Part为兼容BodyPart
     * @param content 文件内容保存
     * @throws MessagingException 异常
     * @throws IOException 异常
     */
    private void getEmailContent(Part msg,StringBuffer content) throws MessagingException, IOException {
       boolean isExistTextAttach =  msg.getContentType().indexOf("name") > 0;
       if (msg.isMimeType("text/*") && !isExistTextAttach){
           content.append(msg.getContent());
       } else if (msg.isMimeType("message/rfc822")){
           getEmailContent((Message) msg.getContent(),content);
       } else if (msg.isMimeType("multipart/*")){
           Multipart multipart = (Multipart) msg.getContent();
           int count = multipart.getCount();
           for (int i = 0; i < count; i++) {
               BodyPart bodyPart = multipart.getBodyPart(i);
               getEmailContent(bodyPart,content);
           }
       }
    }

    /**
     * 验证是否存在附件
     * @param part Part
     * @return 返回
     * @throws MessagingException 异常
     * @throws IOException 异常
     */
    private boolean isExistAttachment(Part part) throws MessagingException, IOException {
        boolean flag = Boolean.FALSE;
        if (part.isMimeType("multipart/*")){
            Multipart multipart = (Multipart) part.getContent();
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String position = bodyPart.getDisposition();
                String contentType = bodyPart.getContentType();
                flag = StringUtils.isNotBlank(position) && (Objects.equals(position,Part.ATTACHMENT) || Objects.equals(position,Part.INLINE)) ? Boolean.TRUE :
                        bodyPart.isMimeType("multipart/*") ? isExistAttachment(bodyPart) :
                                contentType.contains("application") || contentType.contains("name") ? Boolean.TRUE : flag;
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = isExistAttachment((Part)part.getContent());
        }
        return flag;
    }

    private List<Map<String, String>> getAttachments(Part part) throws IOException, MessagingException {
        boolean existAttachment = this.isExistAttachment(part);
        if (!existAttachment){
            return null;
        }
        return null;
    }
}
