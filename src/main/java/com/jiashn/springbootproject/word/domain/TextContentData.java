package com.jiashn.springbootproject.word.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: jiangjs
 * @description: 文本
 * @date: 2022/11/24 15:07
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TextContentData extends LabelData {

    /**
     * 文本内容
     */
    private String content;
}