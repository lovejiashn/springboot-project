package com.jiashn.springbootproject.valid.domain;

import com.jiashn.springbootproject.valid.aop.IdCode;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/6/21 14:48
 **/
@Data
public class Employee {
    @NotBlank(message = "请填写雇员名称")
    private String name;

    @IdCode(message = "请填写正确的身份证信息！")
    private String idCode;
}