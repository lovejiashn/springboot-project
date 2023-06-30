package com.jiashn.springbootproject.reflect.domain;

import lombok.Data;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/6/30 9:41
 **/
@Data
public class Animal {

    private String name;
    private Integer age;
    private String color;

    @Data
    public static class OtherAnimal{
        private String other;
    }
}
