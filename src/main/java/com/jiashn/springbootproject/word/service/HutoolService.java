package com.jiashn.springbootproject.word.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangjs
 * @date 2022-04-03 20:14
 */
public interface HutoolService {

    void getWordByHutool();

    void downLoadWord(HttpServletResponse response);
}
