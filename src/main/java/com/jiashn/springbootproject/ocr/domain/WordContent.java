package com.jiashn.springbootproject.ocr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/10/30 16:46
 **/
@Data
@Accessors(chain = true)
public class WordContent {
    /**
     * 当前页
     */
    private int pageNum= 1;
    /**
     * 文字内容
     */
    private String wordContent;
    /**
     * 图片内容
     */
    private List<String> imageContents;


}
