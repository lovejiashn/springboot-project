package com.jiashn.springbootproject.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/12/8 10:30
 **/
public class Base64UseUtil {
    private final static Logger log = LoggerFactory.getLogger(Base64UseUtil.class);

    /**
     * 文件流转base64
     * @param inputStream 文件流
     * @return base64字符
     */
    public static String base64FromInputStream(InputStream inputStream){
        byte[] bytes = null;
        try {
            bytes = byteFromInputStream(inputStream);
        }catch (Exception e){
            log.error("inputStream流转换base64报错：{}",e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (Objects.nonNull(inputStream)){
                    inputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 文件流转byte
     * @param inputStream 文件流
     * @return 转换后的byte
     */
    public static byte[] byteFromInputStream(InputStream inputStream){
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                bos.write(buff, 0, rc);
            }
            bytes = bos.toByteArray();
        }catch (Exception e){
            log.error("inputStream流转换byte报错：{}",e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (Objects.nonNull(inputStream)){
                    inputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return bytes;
    }
}