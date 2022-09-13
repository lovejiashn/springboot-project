package com.jiashn.springbootproject.ocr.service;

import com.jiashn.springbootproject.utils.ResultUtil;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/9/13 14:44
 **/
public interface OCRRestService {
    /**
     * 获取图片中的文字
     * @return 返回值
     */
    ResultUtil<String> getWordsFromImage();
}
