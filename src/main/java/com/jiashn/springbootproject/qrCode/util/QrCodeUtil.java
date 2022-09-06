package com.jiashn.springbootproject.qrCode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jiashn.springbootproject.qrCode.domain.QrImage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
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
     * 生产二维码（未带中心图片）
     * @param qrImage 图片后缀名
     * @param suffix 二维码图片后缀名
     * @return 返回图片流
     */
    public InputStream createQrCode(QrImage qrImage,String suffix,boolean isNeedCompress){
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos;
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>(4);
            //定义字符编码
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
            //纠错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            //边距
            hints.put(EncodeHintType.MARGIN,2);
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(qrImage.getContent(), BarcodeFormat.QR_CODE, qrImage.getWidth(), qrImage.getHeight(), hints);
            int qrWidth = qrImage.getWidth();
            int qrHeight = qrImage.getHeight();
            BufferedImage image = new BufferedImage(qrWidth,qrHeight,BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < qrWidth; x++) {
                for (int y = 0; y < qrWidth; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            if(StringUtils.isNotEmpty(qrImage.getLogoPath()) &&
                    (qrImage.getLogoPath().endsWith(".png") || qrImage.getLogoPath().endsWith(".jpg"))){
                //生成带logo的二维码
                insertLogoImage(image, qrImage, isNeedCompress);
            }
            baos = new ByteArrayOutputStream();
            ImageIO.write(image,suffix, baos);
            bais = new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e){
            log.error("生成二维码报错："+e.getMessage());
            e.printStackTrace();
        }
        return bais;
    }

    /**
     * 生产二维码
     * @param qrImage 图片后缀名
     * @param suffix 二维码图片后缀名
     * @param isNeedCompress 是否压缩logo
     * @return 返回图片流
     */
    public InputStream createQrCodeImage(QrImage qrImage,String suffix,boolean isNeedCompress){
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
            if(StringUtils.isNotEmpty(qrImage.getLogoPath()) &&
                    (qrImage.getLogoPath().endsWith(".png") || qrImage.getLogoPath().endsWith(".jpg"))){
                //生成带logo的二维码
                BufferedImage bufferedImage = insertLogoImage(file, qrImage, isNeedCompress);
                ImageIO.write(bufferedImage,suffix,file);
            }
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

    private void insertLogoImage(BufferedImage sourceImage,QrImage qrImage,boolean isNeedCompress) throws IOException {
        File logoFile = new File(qrImage.getLogoPath());
        if (!logoFile.exists()){
            log.info("生成的二维码的logo图标不存在");
            return;
        }
        Image read = ImageIO.read(logoFile);
        int width = read.getWidth(null);
        int height = read.getHeight(null);
        if (isNeedCompress){
            width = width > qrImage.getLogoWidth() ? qrImage.getLogoWidth() : width;
            height = height > qrImage.getLogoHeight() ? qrImage.getLogoHeight() : height;
            Image image = read.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            read = image;
        }
        Graphics2D graphics = sourceImage.createGraphics();
        int x = (qrImage.getWidth() - width) / 2;
        int y = (qrImage.getHeight() - width) / 2;
        graphics.drawImage(read,x,y,width,height,null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, height, 0, 0);;
        graphics.setStroke(new BasicStroke(3f));
        graphics.draw(shape);
        graphics.dispose();
    }

    private BufferedImage insertLogoImage(File source,QrImage qrImage,boolean isNeedCompress) throws IOException {
        File logoFile = new File(qrImage.getLogoPath());
        BufferedImage sourceImage = ImageIO.read(source);
        if (!logoFile.exists()){
            log.info("生成的二维码的logo图标不存在");
            return sourceImage;
        }
        Image read = ImageIO.read(logoFile);
        int width = read.getWidth(null);
        int height = read.getHeight(null);
        if (isNeedCompress){
            width = width > qrImage.getLogoWidth() ? qrImage.getLogoWidth() : width;
            height = height > qrImage.getLogoHeight() ? qrImage.getLogoHeight() : height;
            Image image = read.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            read = image;
        }
        Graphics2D graphics = sourceImage.createGraphics();
        int x = (qrImage.getWidth() - width) / 2;
        int y = (qrImage.getHeight() - width) / 2;
        graphics.drawImage(read,x,y,width,height,null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, height, 0, 0);;
        graphics.setStroke(new BasicStroke(3f));
        graphics.draw(shape);
        graphics.dispose();
        return sourceImage;
    }
}