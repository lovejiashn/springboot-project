package com.jiashn.springbootproject.useUtil.seftassert;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: jiangjs
 * @description: 自定义异常基础类
 * @date: 2023/4/28 14:40
 **/
@Getter
@Setter
public class BaseException extends RuntimeException{
    /**
     * 响应码
     */
    private IResponseEnum responseEnum;
    /**
     * 参数信息
     */
    private Object[] objs;

    public BaseException(String message,IResponseEnum responseEnum,Object[] objs){
        super(message);
        this.responseEnum = responseEnum;
        this.objs = objs;
    }

    public BaseException(String message,Throwable cause,IResponseEnum responseEnum,Object[] objs){
        super(message, cause);
        this.responseEnum = responseEnum;
        this.objs = objs;
    }
}
