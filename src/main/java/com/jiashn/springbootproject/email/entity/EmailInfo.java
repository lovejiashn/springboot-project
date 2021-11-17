package com.jiashn.springbootproject.email.entity;

import lombok.Data;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/15 15:03
 **/
@Data
public class EmailInfo {
    /**
     * 是否需要身份验证
     */
    private boolean validate = true;
    /**
     * 是否开启Session的debug模式，可以查看到程序发送Email的运行状态
     */
    private boolean debugMode = false;
    /**
     * 发送邮件的服务器的IP(或主机地址)
     */
    private String mailServerHost;
    /**
     * 发送邮件的服务器的端口
     */
    private String mailServerPort;
    /**
     * 登陆邮件发送服务器的用户名
     */
    private String userName;
    /**
     * 登陆邮件发送服务器的密码
     */
    private String password;

    /**
     * 查询类型
     */
    private String type;

    /**
     * 获取邮件的会话信息
     * @return 返回各种属性值
     */
    public Properties getProperties(){
        Properties properties = new Properties();
        //默认的邮件传输协议
        properties.setProperty("mail.transport.protocol","smtp");
        //默认的存储邮件协议
        properties.setProperty("mail.store.protocol","imap");
        //设置邮件主机名
        properties.setProperty("mail.imap.host",mailServerHost);
        //设置邮件端口号
        properties.setProperty("mail.imap.port",mailServerPort);
        //设置是否安全验证，默认为false
        properties.setProperty("mail.smtp.auth", String.valueOf(this.validate));
        return properties;
    }

    /**
     * 获取邮件的会话信息
     * @return 返回各种属性值
     */
    public Properties getSendProperties(){
        Properties properties = new Properties();
        //默认的邮件传输协议
        properties.setProperty("mail.smtp.auth","true");
        //默认的存储邮件协议
        properties.setProperty("mail.transport.protocol","smtp");
        //设置邮件主机名
        properties.setProperty("mail.smtp.host",mailServerHost);
        return properties;
    }

    /**
     * 邮箱的验证
     * @return 验证结果信息
     */
    public Authenticator getAuthenticator(){
        Authenticator authenticator = null;
        if (isValidate()){
            authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName,password);
                }
            };
        }
        return authenticator;
    }
}