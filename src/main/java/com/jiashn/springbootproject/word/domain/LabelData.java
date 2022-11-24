package com.jiashn.springbootproject.word.domain;

import com.jiashn.springbootproject.word.enums.WordContentTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/11/24 15:05
 **/
@Data
@Accessors(chain = true)
public class LabelData {
    /**
     * 标签名称
     */
    private String labelName;
    /**
     * 文件内容类型
     */
    private WordContentTypeEnum typeEnum;
}