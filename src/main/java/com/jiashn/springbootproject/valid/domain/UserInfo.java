package com.jiashn.springbootproject.valid.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/6/21 10:46
 **/
@Data
public class UserInfo {
    public interface Update{};
    public interface Default{};

    @NotNull(message = "userId不能为空",groups = Update.class)
    private Long userId;

    @NotBlank(message = "用户名不能为空",groups = Default.class)
    private String userName;

    @NotBlank(message = "密码不能为空",groups = Default.class)
    private String password;

}