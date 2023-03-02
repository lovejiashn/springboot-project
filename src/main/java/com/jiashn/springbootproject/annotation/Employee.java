package com.jiashn.springbootproject.annotation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/3/2 9:56
 **/
@Data
@Accessors(chain = true)
public class Employee implements Serializable {
    public static int age = 10;
    private String userName;
    private transient String passWord;

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
         Employee.age = age;
    }
}
