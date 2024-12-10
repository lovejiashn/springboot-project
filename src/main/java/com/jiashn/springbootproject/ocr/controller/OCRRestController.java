package com.jiashn.springbootproject.ocr.controller;

import com.jiashn.springbootproject.ocr.service.OCRRestService;
import com.jiashn.springbootproject.ocr.util.*;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
    @Resource
    private RapidOcrUtil rapidOcrUtil;
    @Resource
    private PdfToWordUtil pdfToWordUtil;
    @Resource
    private RemoveWaterMarkUtil removeWaterMarkUtil;
    @Resource
    private ImageToPDFUtil imageToPDFUtil;
    @Resource
    private ReadImageContentUtil readImageContentUtil;

    @GetMapping("/getWordsFromImage.do")
    public ResultUtil<String> getWordsFromImage(){
       return ocrRestService.getWordsFromImage();
    }

    @GetMapping("/getWords.do")
    public ResultUtil<String> getWords(){
        return ResultUtil.success(rapidOcrUtil.getOcrResult("D:\\image\\1.jpg"));
    }
    @GetMapping("/removeWaterMark.do")
    public ResultUtil<?> removeWaterMark(){
        removeWaterMarkUtil.removeWaterMark("D:\\image\\1.jpg");
        return ResultUtil.success();
    }

    @GetMapping("/getPdfToWords.do")
    public ResultUtil<?> getPdfToWords(){
        return ResultUtil.success(pdfToWordUtil.pdfToWord("D:\\image\\1.pdf"));
    }
    @GetMapping("/getImageToPDF.do")
    public ResultUtil<?> getImageToPDF() throws IOException {
        imageToPDFUtil.imageToPDF("D:\\image\\1.png");
        return ResultUtil.success();
    }
    @GetMapping("/getImageInfo.do")
    public ResultUtil<?> getImageInfo() throws IOException {
        return ResultUtil.success( readImageContentUtil.getImageInfo("D:\\image\\1.png"));
    }


}