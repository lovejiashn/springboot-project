package com.jiashn.springbootproject.useUtil.seftassert;

/**
 * @author: jiangjs
 * @description: 响应码
 * @date: 2023/4/28 14:44
 **/
public interface IResponseEnum {

    /**
     * 返回code码
     *
     * @return code码
     */
    String getCode();

    /**
     * 返回描述信息
     *
     * @return 描述信息
     */
    String getMsg();
}
