package com.jiashn.springbootproject.listener.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/6/23 14:09
 **/
@Data
@AllArgsConstructor
public class AdminUser {
    private String userName;
    private String password;
}