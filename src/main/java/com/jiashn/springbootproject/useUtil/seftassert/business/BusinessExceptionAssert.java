package com.jiashn.springbootproject.useUtil.seftassert.business;

import com.jiashn.springbootproject.useUtil.seftassert.BaseException;
import com.jiashn.springbootproject.useUtil.seftassert.IResponseEnum;
import com.jiashn.springbootproject.useUtil.seftassert.SelfAssert;

/**
 * @author: jiangjs
 * @description: 自定义业务异常断言
 * @date: 2023/4/28 15:06
 **/
public interface BusinessExceptionAssert extends IResponseEnum, SelfAssert {
    @Override
    default BaseException newException(Object... objs){
        return new BaseException(this.getMsg(),this,objs);
    };

    @Override
    default BaseException newException(String msg, Object... objs){
        return new BaseException(msg,this,objs);
    };

    @Override
    default BaseException newException(String msg, Throwable cause, Object... objs){
        return new BaseException(msg,cause,this,objs);
    };
}
