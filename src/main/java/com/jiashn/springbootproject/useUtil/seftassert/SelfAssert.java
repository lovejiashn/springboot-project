package com.jiashn.springbootproject.useUtil.seftassert;

import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/4/28 14:58
 **/
public interface SelfAssert {

    /**
     * 创建自定义异常
     * @param objs 参数信息
     * @return 自定义异常
     */
    BaseException newException(Object... objs);

    /**
     * 创建自定义异常
     * @param msg 描述信息
     * @param objs 参数信息
     * @return 自定义异常
     */
    BaseException newException(String msg,Object... objs);

    /**
     * 创建自定义异常
     * @param msg 描述信息
     * @param cause 接受验证异常
     * @param objs 参数信息
     * @return 自定义异常
     */
    BaseException newException(String msg,Throwable cause,Object... objs);


    /**
     * 校验非空
     * @param obj 被验证对象
     * @param objs 异常信息
     */
    default void assertNotNull(Object obj, Object... objs) {
        if (Objects.isNull(obj)) {
            throw newException(objs);
        }
    }

    /**
     * 校验非空
     * @param obj 被验证对象
     * @param msg 异常描述
     * @param objs 异常信息
     */
    default void assertNotNull(Object obj, String msg, Object... objs) {
        if (Objects.isNull(obj)) {
            throw newException(msg, objs);
        }
    }
}
