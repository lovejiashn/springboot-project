package com.jiashn.springbootproject.qrCode.controller;

import com.jiashn.springbootproject.qrCode.service.QrCodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/26 14:02
 **/
@RestController
@RequestMapping("/qrCode")
public class QrCodeController {

    @Resource
    private QrCodeService qrCodeService;

    @GetMapping("/createUnImageQrCode.do")
    public void createUnImageQrCode(HttpServletResponse response){
        qrCodeService.createUnImageQrCode(response);
    }

    @GetMapping("/createLogoQrCode.do")
    public void createLogoQrCode(HttpServletResponse response){
        qrCodeService.createLogoQrCode(response);
    }
}