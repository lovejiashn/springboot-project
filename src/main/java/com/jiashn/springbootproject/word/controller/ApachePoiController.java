package com.jiashn.springbootproject.word.controller;

import com.jiashn.springbootproject.word.service.ApachePoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangjs
 * @date 2022-04-03 21:50
 */
@RestController
@RequestMapping("/poi")
public class ApachePoiController {
    @Autowired
    private ApachePoiService poiService;

    @GetMapping("/downLoadWord.do")
    public void downLoadWord(HttpServletResponse response){
        poiService.downLoadWord(response);
    }

    @GetMapping("/generatePic.do")
    public void generatePic(){
        poiService.generatePic();
    }
    @GetMapping("/generateGroupChart.do")
    public void generateGroupChart(HttpServletResponse response){
        poiService.generateGroupChart(response);
    }
}
