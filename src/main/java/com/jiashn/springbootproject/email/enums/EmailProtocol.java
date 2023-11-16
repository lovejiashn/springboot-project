package com.jiashn.springbootproject.email.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/6/20 10:52
 **/
public enum EmailProtocol {

    QQ(() -> { Map<String, String> map = new HashMap<>();
        map.put("imap","imap.qq.com");
        map.put("smtp","smtp.qq.com");
        map.put("smtp_port","587");
        return map;
    }),
    QQ_COMPANY(() -> { Map<String, String> map = new HashMap<>();
        map.put("imap","imap.exmail.qq.com");
        map.put("smtp","smtp.exmail.qq.com");
        map.put("smtp_port","25");
        return map;
    }),
    SINA(() -> { Map<String, String> map = new HashMap<>();
        map.put("imap","imap.sina.com");
        map.put("smtp","smtp.sina.com");
        map.put("smtp_port","25");
        return map;
    }),
    EMAIL_163(() -> { Map<String, String> map = new HashMap<>();
        map.put("imap","imap.163.com");
        map.put("smtp","smtp.163.com");
        map.put("smtp_port","25");
        return map;
    }),
    EMAIL_126(() -> { Map<String, String> map = new HashMap<>();
        map.put("imap","imap.126.com");
        map.put("smtp","smtp.126.com");
        map.put("smtp_port","25");
        return map;
    }),
    EMAIL_139(() -> { Map<String, String> map = new HashMap<>();
        map.put("imap","imap.139.com");
        map.put("smtp","smtp.139.com");
        map.put("smtp_port","25");
        return map;
    }),
    ALI_YUN(() -> { Map<String, String> map = new HashMap<>();
        map.put("imap","imap.aliyun.com");
        map.put("smtp","smtp.aliyun.com");
        map.put("smtp_port","25");
        return map;
    });

    private final ProtocolData protocolData;

    EmailProtocol(ProtocolData protocol){
        this.protocolData = protocol;
    }

    public ProtocolData getProtocolData() {
        return protocolData;
    }
}
