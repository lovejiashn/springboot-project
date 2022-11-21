package com.jiashn.springbootproject.down.controller;

import com.jiashn.springbootproject.down.service.DownFileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author: jiangjs
 * @description: 下载文件
 * @date: 2022/11/21 10:20
 **/
@RestController
@RequestMapping("/down/file")
public class DownFileController {
    @Resource
    private DownFileService downFileService;
    @GetMapping("/multiDownFile.do")
    public void multiDownFile(MultipartFile file){
        downFileService.multiDownFile(file);
    }
}