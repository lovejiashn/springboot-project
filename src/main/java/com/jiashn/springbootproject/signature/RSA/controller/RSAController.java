package com.jiashn.springbootproject.signature.RSA.controller;

import com.jiashn.springbootproject.signature.RSA.RSAUtil;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/11/13 10:51
 **/
@RestController
@RequestMapping("/rsa")
public class RSAController {

    @Resource
    private RSAUtil rsaUtil;

    @GetMapping("/generateKeys.do")
    public ResultUtil<Map<String,String>> generateKeys(){
        Map<String,String> keys = new HashMap<>(2);
        keys.put("public",rsaUtil.generatePublicKey());
        keys.put("private",rsaUtil.generatePrivateKey());
        return ResultUtil.success(keys);
    }

    @PostMapping("/encryptPublicKey.do")
    public ResultUtil<String> EncryptPublicKey(@RequestParam("phone") String phone,
                                               @RequestParam("publicKey") String publicKey,
                                               @RequestParam("privateKey") String privateKey){
        return ResultUtil.success(rsaUtil.encryptByPublicKey(phone,publicKey,privateKey));
    }

    @PostMapping("/encryptPrivateKey.do")
    public ResultUtil<String> EncryptPrivateKey(@RequestParam("phone") String phone,
                                                @RequestParam("privateKey") String privateKey){
        return ResultUtil.success(rsaUtil.encryptByPrivateKey(phone,privateKey));
    }

    @PostMapping("/decryptPublicKey.do")
    public ResultUtil<String> decryptPublicKey(@RequestParam("content") String content,
                                               @RequestParam("publicKey") String publicKey){
        return ResultUtil.success(rsaUtil.decryptByPublicKey(content,publicKey));
    }

    @PostMapping("/decryptPrivateKey.do")
    public ResultUtil<String> decryptPrivateKey(@RequestParam("content") String content,
                                                @RequestParam("privateKey") String privateKey){
        return ResultUtil.success(rsaUtil.decryptByPrivateKey(content,privateKey));
    }

    @GetMapping("/test.do")
    public ResultUtil<String> test(){
        String privateKey = rsaUtil.generatePrivateKey();
        String publicKey = rsaUtil.generatePublicKey();
        String s = rsaUtil.encryptByPublicKey("13456789012", publicKey, privateKey);
        String decrypt = rsaUtil.decryptByPrivateKey(s, privateKey);
        return ResultUtil.success(decrypt);
    }

}
