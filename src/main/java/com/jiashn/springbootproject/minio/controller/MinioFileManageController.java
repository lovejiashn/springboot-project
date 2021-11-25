package com.jiashn.springbootproject.minio.controller;

import com.jiashn.springbootproject.minio.service.MinioFileManageService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/22 9:53
 **/
@RestController
@RequestMapping("/minio")
public class MinioFileManageController {

    @Autowired
    private MinioFileManageService fileManageService;

    @RequestMapping("/upLoadFiles.do")
    public ResultUtil<?> upLoadFiles(MultipartFile[] files){
        return fileManageService.upLoadFiles(files);
    }

    @GetMapping("/downFile.do/{filePath}")
    public ResultUtil<?> downFile(@PathVariable("filePath") String filePath){
        return fileManageService.downFile(filePath);
    }

    @RequestMapping("/upLoadFileBackUrl.do")
    public ResultUtil<List<String>> upLoadFileBackUrl(MultipartFile[] files){
        return fileManageService.upLoadFileBackUrl(files);
    }

    @GetMapping("/removeFile.do/{filePath}")
    public ResultUtil<?> removeFile(@PathVariable("filePath") String filePath){
        return fileManageService.removeFile(filePath);
    }
}