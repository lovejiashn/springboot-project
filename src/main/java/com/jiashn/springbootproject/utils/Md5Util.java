package com.jiashn.springbootproject.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @Author: jiangjs
 * @Description: md5加密
 * @Date: 2021/11/22 10:47
 **/
public class Md5Util {

    private static final Logger log = LoggerFactory.getLogger(Md5Util.class);

    /**
     * md5字符串加密
     * @param encodeStr 需加密串
     * @return 返回结果
     */
    public static String md5Encode(String encodeStr){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(encodeStr.getBytes(StandardCharsets.UTF_8));
            return getMd5(digest);
        } catch (Exception e) {
            log.error("数据进行MD5加密报错：{}",e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    public static String md5Encode(InputStream inputStream){
        try {
            byte[] bytes = new byte[1024];
            MessageDigest md = MessageDigest.getInstance("MD5");
            int length = 0;
            while ((length = inputStream.read(bytes)) != -1) {
                md.update(bytes, 0, length);
            }
            return getMd5(bytes);
        }catch (Exception e){
            log.error("文件流数据进行MD5加密报错：{}",e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    private static String getMd5(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            if (Integer.toHexString(0xFF & b).length() == 1) {
                builder.append("0").append(Integer.toHexString(0xFF & b));
            } else {
                builder.append(Integer.toHexString(0xFF & b));
            }
        }
        return String.valueOf(builder);
    }
}