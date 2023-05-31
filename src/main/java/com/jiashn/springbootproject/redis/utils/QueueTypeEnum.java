package com.jiashn.springbootproject.redis.utils;

/**
 * @author: jiangjs
 * @description: 队列类型
 * @date: 2023/5/30 10:53
 **/
public enum QueueTypeEnum {

    /**
     * 订单
     */
    ORDER("order");

    private final String type;

    QueueTypeEnum(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
