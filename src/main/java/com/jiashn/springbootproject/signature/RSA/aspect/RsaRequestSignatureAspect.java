package com.jiashn.springbootproject.signature.RSA.aspect;

import com.alibaba.fastjson.JSONObject;
import com.jiashn.springbootproject.signature.RSA.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/11/16 14:47
 **/
@Aspect
@Slf4j
@Component
public class RsaRequestSignatureAspect {

    @Resource
    private ResourceLoader resourceLoader;

    private final static String WECHAT_RSA_KEYS_FILE_PATH = "classpath:/static/rsa/wechat_rsa_keys.txt";

    @Pointcut("@annotation(com.jiashn.springbootproject.signature.RSA.aspect.RsaVerifySignature)")
    public void rsaVerifySignature(){};

    @Before("rsaVerifySignature()")
    public void verifySignature(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取手机号
        Assert.notNull(request,"获取请求失败");

        String phone = request.getHeader("phone");
        String sign = request.getHeader("sign");
        //读取rsa密钥
        try(InputStream keyIsm = resourceLoader.getResource(WECHAT_RSA_KEYS_FILE_PATH).getInputStream()){
            Assert.notNull(keyIsm,"读取rsa密钥失败");
            String keys = IOUtils.toString(keyIsm, StandardCharsets.UTF_8);
            JSONObject keyJson = JSONObject.parseObject(keys);
            String privateKey = keyJson.getString("private");
            String checkedPhone = RSAUtil.decryptByPrivateKey(sign, privateKey);
            if (!Objects.equals(checkedPhone,phone)){
                throw new RuntimeException("微信小程序验签不通过");
            }
        }catch (Exception e) {
            throw new RuntimeException("获取rsa密钥报错");
        }
    }
}
