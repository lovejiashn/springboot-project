package com.jiashn.springbootproject.word.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangjs
 * @date 2022-04-03 21:52
 */
public interface ApachePoiService {

    void downLoadWord(HttpServletResponse response);

    void generatePic();

    void generateGroupChart(HttpServletResponse response);
}
