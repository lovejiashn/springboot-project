package com.jiashn.springbootproject.qrCode.domain;

import lombok.Data;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/26 11:09
 **/
@Data
public abstract class QrMiddleImage {

    /**
     * 嵌入二维码中图片路径
     */
    private String imgPath;
    /**
     * 嵌入二维码中图片宽度
     */
    private Integer imgWidth;

    /**
     * 嵌入二维码中图片高度
     */
    private Integer imgHeight;

    /** 嵌入的图片与二维码图片之间的框的宽度(px) */
    private Integer frameWidth;

    /** 嵌入的图片与二维码图片之间的框的颜色. */
    private Integer frameWidthColor;

}