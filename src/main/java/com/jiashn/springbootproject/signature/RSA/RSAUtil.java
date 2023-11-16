package com.jiashn.springbootproject.signature.RSA;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

/**
 * @author: jiangjs
 * @description: 基于hutool工具的RSA加解密
 * @date: 2023/11/13 10:17
 **/
@Component
public class RSAUtil {

    private final static KeyPair keyPair = SecureUtil.generateKeyPair("RSA");

    /**
     * 获取RSA公钥
     * @return base64的公钥
     */
    public static String generatePublicKey(){
        return Base64.encode(keyPair.getPublic().getEncoded());
    }

    /**
     * 获取RSA私钥
     * @return base64的私钥
     */
    public static String generatePrivateKey(){
        return Base64.encode(keyPair.getPrivate().getEncoded());
    }

    /**
     * 内容进行私钥加密
     * @param content 需加密内容
     * @param privateKey 私钥
     * @return 加密后信息
     */
    public static String encryptByPrivateKey(String content,String privateKey){
        RSA rsa = new RSA(privateKey,null);
        return rsa.encryptHex(content, KeyType.PrivateKey);
    }

    /**
     * 内容进行加密
     * @param content 需加密内容
     * @param publicKey 公钥
     * @return 加密后信息
     */
    public static String encryptByPublicKey(String content,String publicKey,String privateKey){
        RSA rsa = new RSA(privateKey.replace(" ","+"),publicKey.replace(" ","+"));
        return rsa.encryptBase64(content, KeyType.PublicKey);
    }

    /**
     * RSA私钥解密
     * @param encryptContent 公钥加密内容
     * @param privateKey 私钥
     * @return 解密后的信息
     */
    public static String decryptByPrivateKey(String encryptContent,String privateKey){
        RSA rsa = new RSA(privateKey, null);
        return rsa.decryptStr(encryptContent, KeyType.PrivateKey, StandardCharsets.UTF_8);
    }

    /**
     * RSA公钥解密
     * @param encryptContent 私钥加密后的内容
     * @param publicKey 公钥
     * @return 解密后的信息
     */
    public static String decryptByPublicKey(String encryptContent,String publicKey){
        RSA rsa = new RSA(null, publicKey);
        return rsa.decryptStr(encryptContent, KeyType.PublicKey,StandardCharsets.UTF_8);
    }
}
