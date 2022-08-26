package com.jiashn.springbootproject.qrCode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jiashn.springbootproject.qrCode.domain.QrImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/26 11:00
 **/
@Component
@SuppressWarnings("unused")
public class QrCodeUtil {

    private static final Logger log = LoggerFactory.getLogger(QrCodeUtil.class);
    @Value("${qr_image_path}")
    private String qrImagePath;

    /**
     * 生产二维码
     * @param qrImage 图片后缀名
     * @param suffix 二维码图片后缀名
     * @return 返回图片流
     */
    public InputStream createQrCode(QrImage qrImage,String suffix){
        //生成临时文件，生成后删除
        //文件名称
        String fileName = UUID.randomUUID() + "." + suffix;
        File file = new File(qrImagePath + fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            Map<EncodeHintType, Object> hints = new HashMap<>(4);
            //定义字符编码
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
            //纠错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            //边距
            hints.put(EncodeHintType.MARGIN,2);
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(qrImage.getContent(), BarcodeFormat.QR_CODE, qrImage.getWidth(), qrImage.getHeight(), hints);
            MatrixToImageWriter.writeToStream(bitMatrix,suffix,fos);
            return new FileInputStream(file);
        } catch (Exception e){
            log.error("生成二维码报错："+e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(fos)){
                    fos.close();
                }
                file.deleteOnExit();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}