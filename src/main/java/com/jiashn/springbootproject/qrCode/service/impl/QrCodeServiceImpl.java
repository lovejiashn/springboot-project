package com.jiashn.springbootproject.qrCode.service.impl;

import com.jiashn.springbootproject.qrCode.domain.QrImage;
import com.jiashn.springbootproject.qrCode.service.QrCodeService;
import com.jiashn.springbootproject.qrCode.util.QrCodeUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/26 14:04
 **/
@Service
public class QrCodeServiceImpl implements QrCodeService {

    public static final Logger log = LoggerFactory.getLogger(QrCodeServiceImpl.class);

    @Resource
    private QrCodeUtil qrCodeUtil;

    @Override
    public void createUnImageQrCode(HttpServletResponse response) {
        QrImage qrImage = new QrImage();
        qrImage.setContent("这是测试的二维码内容");
        InputStream inputStream = qrCodeUtil.createQrCode(qrImage, "png",false);
        ServletOutputStream sops = null;
        if (Objects.nonNull(inputStream)){
            try {
                String imageName = "二维码.png";
                response.setHeader("Content-Type","application/octet-stream");
                response.setHeader("Content-Disposition","attachment;filename="+new String(imageName.getBytes(), StandardCharsets.ISO_8859_1));
                sops = response.getOutputStream();
                sops.write(IOUtils.toByteArray(inputStream));
                sops.flush();
            }catch (Exception e){
                log.error("生成二维码报错："+ e.getMessage());
                e.printStackTrace();
            }finally {
                try {
                    if (Objects.nonNull(sops)){
                        sops.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void createLogoQrCode(HttpServletResponse response) {
        QrImage qrImage = new QrImage();
        qrImage.setContent("带logo的二维码").setLogoPath("d:\\图片\\标配.jpg");
        InputStream inputStream = qrCodeUtil.createQrCodeImage(qrImage, "png",true);
        ServletOutputStream sops = null;
        if (Objects.nonNull(inputStream)){
            try {
                String imageName = "二维码.png";
                response.setHeader("Content-Type","application/octet-stream");
                response.setHeader("Content-Disposition","attachment;filename="+new String(imageName.getBytes(), StandardCharsets.ISO_8859_1));
                sops = response.getOutputStream();
                sops.write(IOUtils.toByteArray(inputStream));
                sops.flush();
            }catch (Exception e){
                log.error("生成二维码报错："+ e.getMessage());
                e.printStackTrace();
            }finally {
                try {
                    if (Objects.nonNull(sops)){
                        sops.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}