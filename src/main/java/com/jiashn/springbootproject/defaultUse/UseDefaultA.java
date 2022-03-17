package com.jiashn.springbootproject.defaultUse;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/26 15:58
 **/
public interface UseDefaultA {

    /**
     * 假设发送邮件
     */
    default void sendEmail(){
        System.out.println("邮件A已经发送");
    }
}
