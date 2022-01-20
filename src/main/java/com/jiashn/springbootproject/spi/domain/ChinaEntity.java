package com.jiashn.springbootproject.spi.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/19 14:41
 **/
@Data
@TableName("china")
public class ChinaEntity {
    private Integer code;
    private String name;
    private Integer parentCode;
    private Integer level;
    private Integer codeShort;
    private String qhmjc;
    private Integer sftbcs;
}