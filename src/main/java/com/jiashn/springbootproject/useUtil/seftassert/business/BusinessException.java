package com.jiashn.springbootproject.useUtil.seftassert.business;

import com.jiashn.springbootproject.useUtil.seftassert.BaseException;
import com.jiashn.springbootproject.useUtil.seftassert.IResponseEnum;

/**
 * @author: jiangjs
 * @description: 自定义业务异常
 * @date: 2023/4/28 15:05
 **/
public class BusinessException extends BaseException {
    public BusinessException(String message, IResponseEnum responseEnum, Object[] objs) {
        super(message, responseEnum, objs);
    }

    public BusinessException(String message, Throwable cause, IResponseEnum responseEnum, Object[] objs) {
        super(message, cause, responseEnum, objs);
    }
}
