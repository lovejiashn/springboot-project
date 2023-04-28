package com.jiashn.springbootproject.useUtil.seftassert.business;

import com.jiashn.springbootproject.useUtil.seftassert.IResponseEnum;

/**
 * @author: jiangjs
 * @description: 用枚举的方式，代替 BadLicenceException、UserNotFoundException 自定义异常
 * @date: 2023/4/28 15:12
 **/
public enum ResponseEnum implements IResponseEnum,BusinessExceptionAssert {
    /**
     * 无权限访问
     */
    BAD_LICENCE("0001", "无权限访问"),
    /**
     * 用户不存在
     */
    USER_NOT_FOUND("1001", "用户不存在");

    private final String code, msg;

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
