package com.jiashn.springbootproject.down.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/11/21 10:23
 **/
public interface DownFileService {
    /**
     * 多线程下载文件
     * @param file 文件
     */
    void multiDownFile(MultipartFile file);
}
