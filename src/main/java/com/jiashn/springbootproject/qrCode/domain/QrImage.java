package com.jiashn.springbootproject.qrCode.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/26 10:41
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrImage extends QrMiddleImage {
    /**
     * 二维码宽度
     */
    @Builder.Default
    private Integer width = 500;
    /**
     * 二维码高度
     */
    @Builder.Default
    private Integer height = 500;
    /**
     * 二维码的内容
     */
    @NotBlank(message = "二维码内容不能为空")
    private String content;
    /**
     * 文字内容
     */
    private String wordContent;
    /**
     * 二维码文字的大小
     */
    @Builder.Default
    private Integer wordSize = 12;
}