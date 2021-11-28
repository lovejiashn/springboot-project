package com.jiashn.springbootproject.office.controller;

import com.jiashn.springbootproject.office.service.OpenOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangjs
 * @date 2021-11-28 8:40
 */
@RestController
@RequestMapping("/aspose/office")
public class AsposeOfficePreviewController {

    @Autowired
    private OpenOfficeService openOfficeService;

    @GetMapping("/preview")
    public void officePreview(@RequestParam("param") String param, HttpServletResponse response){
        openOfficeService.asposeOfficeOnlinePreview(param,response);
    }
}
