package com.jiashn.springbootproject.signature.RSA.aspect;

import java.lang.annotation.*;

/**
 * @author: jiangjs
 * @description: rsa加解密demo
 * @date: 2023/11/16 14:45
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface RsaVerifySignature {
}
