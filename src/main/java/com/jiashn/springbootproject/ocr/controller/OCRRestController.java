package com.jiashn.springbootproject.ocr.controller;

import com.jiashn.springbootproject.ocr.service.OCRRestService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jiangjs
 * @description: 识别图片中的文字
 * @date: 2022/9/13 14:43
 **/
@RestController
@RequestMapping("/ocr")
public class OCRRestController {

    @Resource
    private OCRRestService ocrRestService;

    @GetMapping("/getWordsFromImage.do")
    public ResultUtil<String> getWordsFromImage(){
       return ocrRestService.getWordsFromImage();
    }
}