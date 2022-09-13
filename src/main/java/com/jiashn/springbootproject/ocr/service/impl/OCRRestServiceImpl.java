package com.jiashn.springbootproject.ocr.service.impl;

import com.jiashn.springbootproject.ocr.service.OCRRestService;
import com.jiashn.springbootproject.ocr.util.OCRUtil;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/9/13 14:45
 **/
@Service
public class OCRRestServiceImpl implements OCRRestService {
    @Resource
    private OCRUtil ocrUtil;
    @Override
    public ResultUtil<String> getWordsFromImage() {
        String path = "D:\\图片\\44444.png";
        File file = new File(path);
        return ocrUtil.getOCRInformation(file);
    }
}