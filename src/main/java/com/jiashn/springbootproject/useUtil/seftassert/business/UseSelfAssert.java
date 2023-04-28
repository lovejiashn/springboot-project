package com.jiashn.springbootproject.useUtil.seftassert.business;

import com.jiashn.springbootproject.useUtil.domain.Expert;

/**
 * @author: jiangjs
 * @description: 使用自定义断言
 * @date: 2023/4/28 15:14
 **/
public class UseSelfAssert {

    public static void main(String[] args) {
        Expert expert = null;
        ResponseEnum.USER_NOT_FOUND.assertNotNull(expert);
    }
}
