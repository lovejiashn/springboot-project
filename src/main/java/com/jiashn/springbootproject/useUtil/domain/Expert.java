package com.jiashn.springbootproject.useUtil.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/4/28 11:12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expert {
    private String name;
    private String gender;
    private Integer age;
}
