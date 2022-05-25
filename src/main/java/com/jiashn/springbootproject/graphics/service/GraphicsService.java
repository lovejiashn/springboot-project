package com.jiashn.springbootproject.graphics.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/5/24 16:02
 **/
public interface GraphicsService {
    /**
     * 生成电子签章
     */
    void getSealImage(HttpServletResponse response);
}
