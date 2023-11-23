package com.jiashn.springbootproject.useUtil;

import java.util.function.Supplier;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/11/23 10:42
 **/
public enum TaskCodeEnum {

    TASK(() -> "T"+System.currentTimeMillis()),
    SUB_TASK(() -> "S"+System.currentTimeMillis()),
    IMP_TASK(() -> "I"+System.currentTimeMillis());

    private final Supplier<String> taskCode;

    TaskCodeEnum(Supplier<String> taskCode) {
        this.taskCode = taskCode;
    }

    public Supplier<String> getTaskCode(){
        return this.taskCode;
    }
}
