package com.jiashn.springbootproject.email.entity;

import com.jiashn.springbootproject.email.enums.EmailProtocol;
import lombok.Data;

/**
 * @author: jiangjs
 * @description: 协议
 * @date: 2023/6/20 10:49
 **/
@Data
public class ReceiveEmail {

    private String receiverEmail;
    private String receiverPassword;
    private EmailProtocol protocol = EmailProtocol.EMAIL_163;
}
