package com.jiashn.springbootproject.desensitize.service;

import java.util.function.Function;

/**
 * @author: jiangjs
 * @description: 脱敏接口
 * @date: 2022/4/15 16:22
 **/
public interface Desensitized extends Function<String,String> {
}
