package com.jiashn.springbootproject.openOffice.controller;

import com.jiashn.springbootproject.openOffice.service.OpenOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/25 11:16
 **/
@RestController
@RequestMapping("/file")
public class OpenOfficeOnlinePreviewController {

    @Autowired
    private OpenOfficeService openOfficeService;

    @GetMapping("/online/preview")
    public void onlinePreviewFile(@RequestParam("param") String param, HttpServletResponse response){
        openOfficeService.openOfficeOnlinePreview(param,response);
    }
}