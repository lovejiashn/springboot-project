package com.jiashn.springbootproject.ocr.util;

import com.benjaminwan.ocrlibrary.OcrResult;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.ParamConfig;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author: jiangjs
 * @description: 去除水印
 * @date: 2024/10/30 15:04
 **/
@Component
public class RapidOcrUtil {

    public String getOcrResult(String imagePath){
        ParamConfig config = ParamConfig.getDefaultConfig();
        config.setDoAngle(Boolean.TRUE);
        config.setMostAngle(Boolean.TRUE);
       // config.setMaxSideLen(1);
      //  config.setPadding(60);
        InferenceEngine instance = InferenceEngine.getInstance(Model.ONNX_PPOCR_V3);
        OcrResult ocrResult = instance.runOcr(new File(imagePath).toString(),config);
        return ocrResult.getStrRes().trim();
    }
}
