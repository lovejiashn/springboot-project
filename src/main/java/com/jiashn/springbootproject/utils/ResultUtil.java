package com.jiashn.springbootproject.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/12 15:46
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultUtil<T> {
    private int code;
    private String msg;
    private T data;

    public static ResultUtil<?> success(String msg){
        return ResultUtil.builder()
                .code(1000)
                .msg(msg)
                .build();
    }

    public static ResultUtil<?> success(){
        return ResultUtil.builder().code(1000).msg("成功").build();
    }
    public static <T> ResultUtil<T> success(T data){
        return ResultUtil.<T>builder().code(1000).msg("成功").data(data).build();
    }

    public static ResultUtil<?> error(String msg){
        return ResultUtil.builder().code(5000).msg(msg).build();
    }

    public static ResultUtil<?> error(int code,String msg){
        return ResultUtil.builder().code(code).msg(msg).build();
    }
}