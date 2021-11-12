package com.jiashn.springbootproject.utils;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/12 15:46
 **/
@Data
@Accessors(chain = true)
public class ResultUtil<T> {
    private int code;
    private String msg;
    private T data;

    public static ResultUtil<?> success(String msg){
        return new ResultUtil<>()
                .setCode(1000).setMsg(msg);
    }

    public static ResultUtil<?> success(){
        return new ResultUtil<>()
                .setCode(1000).setMsg("成功");
    }
    public static <T> ResultUtil<T> success(T data){
        return new ResultUtil<T>()
                .setCode(1000)
                .setMsg("成功")
                .setData(data);
    }

    public static ResultUtil<?> error(String msg){
        return new ResultUtil<>()
                .setCode(5000)
                .setMsg(msg);
    }

    public static ResultUtil<?> error(int code,String msg){
        return new ResultUtil<>()
                .setCode(code)
                .setMsg(msg);
    }
}