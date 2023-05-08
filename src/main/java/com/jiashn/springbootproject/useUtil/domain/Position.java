package com.jiashn.springbootproject.useUtil.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: jiangjs
 * @description: 职位
 * @date: 2023/5/8 11:20
 **/
@Data
@AllArgsConstructor
public class Position {

    /**
     * 职位名称
     */
    private String name;
    /**
     * 职位等级
     */
    private Integer level;

}
