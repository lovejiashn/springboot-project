package com.jiashn.springbootproject.ocr.util;

import com.jiashn.springbootproject.utils.ResultUtil;
import net.sourceforge.tess4j.Tesseract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/9/13 14:47
 **/
@Component
public class OCRUtil {

    private static final List<String> FILE_SUFFIX = Arrays.asList("png","jpg","jpeg");
    private static final Logger log = LoggerFactory.getLogger(OCRUtil.class);

    @Value("${tess4j.tessdata.path}")
    private String dataPath;

    public ResultUtil<String> getOCRInformation(File file){
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        if (!FILE_SUFFIX.contains(suffix)){
            return ResultUtil.error("上传的文件格式不正确，请上传常用图片格式");
        }
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(dataPath);
        // chi_sim ：简体中文， eng    根据需求选择语言库
        tesseract.setLanguage("chi_sim+eng");
        try {
            String result = tesseract.doOCR(file);
            return ResultUtil.success(result);
        }catch (Exception e){
            log.error("获取图片中的文字报错：" + e.getMessage());
            e.printStackTrace();
            return ResultUtil.error("程序报错，获取失败");
        }
    }
}