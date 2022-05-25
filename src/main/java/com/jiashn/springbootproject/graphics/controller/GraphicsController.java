package com.jiashn.springbootproject.graphics.controller;

import com.jiashn.springbootproject.graphics.service.GraphicsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: jiangjs
 * @description: Graphics2D操作图片
 * @date: 2022/5/24 16:00
 **/
@RestController
@RequestMapping("/graphics")
public class GraphicsController {

    @Resource
    private GraphicsService graphicsService;

    @GetMapping("/getSealImage.do")
    private void getSealImage(HttpServletResponse response) {
        graphicsService.getSealImage(response);
    }
}