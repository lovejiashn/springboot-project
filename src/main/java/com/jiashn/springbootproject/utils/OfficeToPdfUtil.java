package com.jiashn.springbootproject.utils;

import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.core.office.OfficeUtils;
import org.jodconverter.local.JodConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/25 14:52
 **/
@Component
public class OfficeToPdfUtil {

    private final static Logger log = LoggerFactory.getLogger(OfficeToPdfUtil.class);

    @Value("${openoffice-path}")
    private String officeHome;

    public InputStream officeToPdf(String sourceFile, String destFile){
        OfficeManager manager = LocalOfficeManager.builder().install()
                .officeHome(officeHome)
                .build();
        try {
            File inFile = new File(sourceFile);
            File outFile = new File(destFile);
            if (!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }
            manager.start();
            JodConverter.convert(inFile).to(outFile).execute();
            InputStream inputStream = new FileInputStream(destFile);
            //为节约空间，删除已转换后的文件
            return inputStream;
        }catch (Exception e){
            log.error("office转换pdf报错：{}",e.getMessage());
            e.printStackTrace();
            return null;
        }finally {
            OfficeUtils.stopQuietly(manager);
        }
    }
    public InputStream officeToPdf(File sourceFile, String destFile){
        OfficeManager manager = LocalOfficeManager.builder().install()
                .officeHome(officeHome)
                .build();
        try {
            File outFile = new File(destFile);
            if (!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }
            manager.start();
            JodConverter.convert(sourceFile).to(outFile).execute();
            InputStream inputStream = new FileInputStream(destFile);
            //为节约空间，删除已转换后的文件
            return inputStream;
        }catch (Exception e){
            log.error("office转换pdf报错：{}",e.getMessage());
            e.printStackTrace();
            return null;
        }finally {
            OfficeUtils.stopQuietly(manager);
        }
    }
}