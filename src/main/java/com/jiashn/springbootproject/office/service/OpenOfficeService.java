package com.jiashn.springbootproject.office.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/25 10:03
 **/
public interface OpenOfficeService {

    /**
     * 在线打开office
     * @param param 传递参数。即：url,或minio的MD5
     * @param response 响应
     */
    void openOfficeOnlinePreview(String param, HttpServletResponse response);

    /**
     * 在线打开office
     * @param param 传递参数。即：url,或minio的MD5
     * @param response 响应
     */
    void asposeOfficeOnlinePreview(String param, HttpServletResponse response);
}
