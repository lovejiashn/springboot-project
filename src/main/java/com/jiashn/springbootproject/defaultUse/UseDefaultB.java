package com.jiashn.springbootproject.defaultUse;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/26 16:12
 **/
public interface UseDefaultB {
    /**
     * 假设发送邮件
     */
    default void sendEmail(){
        System.out.println("邮件B已经发送");
    }
}
