package com.jiashn.springbootproject.qrCode.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/26 14:04
 **/
public interface QrCodeService {
    /**
     * 生成无图片的二维码
     * @param response 响应
     */
    void createUnImageQrCode(HttpServletResponse response);
}