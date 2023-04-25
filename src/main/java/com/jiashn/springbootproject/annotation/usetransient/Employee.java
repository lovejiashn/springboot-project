package com.jiashn.springbootproject.annotation.usetransient;

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
    public transient int age;
    private String userName;
    private transient String passWord;
}
