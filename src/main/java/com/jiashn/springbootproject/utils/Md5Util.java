package com.jiashn.springbootproject.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @Author: jiangjs
 * @Description: md5加密
 * @Date: 2021/11/22 10:47
 **/
public class Md5Util {

    private static final Logger log = LoggerFactory.getLogger(Md5Util.class);

    public static String md5Encode(String encodeStr){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(encodeStr.getBytes("UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (byte b : digest) {
                if (Integer.toHexString(0xFF & b).length() == 1) {
                    builder.append("0").append(Integer.toHexString(0xFF & b));
                } else {
                    builder.append(Integer.toHexString(0xFF & b));
                }
            }
            return String.valueOf(builder);
        } catch (Exception e) {
            log.error("数据进行MD5加密报错：{}",e.getMessage());
            e.printStackTrace();
            return "";
        }
    }
}