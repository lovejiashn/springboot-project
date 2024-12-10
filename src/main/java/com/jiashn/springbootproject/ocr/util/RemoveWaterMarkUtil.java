package com.jiashn.springbootproject.ocr.util;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/10/31 11:47
 **/
@Component
public class RemoveWaterMarkUtil {

    public List<String> removeWaterMark(List<String> imagePaths){
        BufferedImage bi = null;
        try {
            for (String path : imagePaths) {
                File file = new File(path);
                if (!file.getName().endsWith("png") && !file.getName().endsWith("jpg") && !file.getName().endsWith("jpeg") ) {
                    continue;
                }
                bi = ImageIO.read(file); //用ImageIO流读取像素块
                if (Objects.nonNull(bi)) {
                    removeWatermark(bi);
                    String formatName = "D:\\image\\" + UUID.randomUUID() + ".png";//生成的图片格式
                    ImageIO.write(bi, formatName, file);//用ImageIO流生成的处理图替换原图片
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void removeWaterMark(String imagePath){
        OpenCV.loadShared();
        Mat image = Imgcodecs.imread(imagePath);
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BayerGRBG2RGBA);
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 120, 255, Imgproc.THRESH_BINARY);
        Mat result = new Mat();
        /**
         * src: 输入图像，填单通道，单8位浮点类型Mat即可。
         * dst: 函数运算后的结果存放在这。即为输出图像（与输入图像同样的尺寸和类型）。
         * maxValue: 预设满足条件的最大值。
         * adaptiveMethod: 指定自适应阈值算法。可选择ADAPTIVE_THRESH_MEAN_C 或 ADAPTIVE_THRESH_GAUSSIAN_C两种。
         * thresholdType：指定阈值类型。可选择THRESH_BINARY或者THRESH_BINARY_INV两种。（即二进制阈值或反二进制阈值）。
         * blockSize：表示邻域块大小，用来计算区域阈值，一般选择为3、5、7......等。
         * C：参数C表示与算法有关的参数，它是一个从均值或加权均值提取的常数，可以是负数。（具体见下面的解释）。
         **/
        Photo.inpaint(image, binary, result, 3, Photo.INPAINT_TELEA);
       // Imgproc.adaptiveThreshold(red,removeRedTh,255,Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY,13,40);
        Imgcodecs.imwrite("D:\\image\\333.png", result);
    }

    public void removeWordWaterMark(String imagePath){
        OpenCV.loadShared();
        Mat image = Imgcodecs.imread(imagePath);
        Mat mask = new Mat(image.size(), CvType.CV_8UC1, new Scalar(0));
        Mat result = new Mat();
        Photo.inpaint(image, mask, result, 3, Photo.INPAINT_TELEA);
        /**
         * src: 输入图像，填单通道，单8位浮点类型Mat即可。
         * dst: 函数运算后的结果存放在这。即为输出图像（与输入图像同样的尺寸和类型）。
         * maxValue: 预设满足条件的最大值。
         * adaptiveMethod: 指定自适应阈值算法。可选择ADAPTIVE_THRESH_MEAN_C 或 ADAPTIVE_THRESH_GAUSSIAN_C两种。
         * thresholdType：指定阈值类型。可选择THRESH_BINARY或者THRESH_BINARY_INV两种。（即二进制阈值或反二进制阈值）。
         * blockSize：表示邻域块大小，用来计算区域阈值，一般选择为3、5、7......等。
         * C：参数C表示与算法有关的参数，它是一个从均值或加权均值提取的常数，可以是负数。（具体见下面的解释）。
         **/
       // Imgproc.adaptiveThreshold(red,removeRedTh,255,Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY,13,40);
        Imgcodecs.imwrite("D:\\image\\333.png", result);
    }


    private static void removeWatermark(BufferedImage bi) {
        Color wColor = new Color(254, 254, 254);
        //白底水印
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                int color = bi.getRGB(i, j);
                Color oriColor = new Color(color);
                int red = oriColor.getRed();
                int greed = oriColor.getGreen();
                int blue = oriColor.getBlue();
                if (red == 254 && greed == 254 && blue == 254) {
                    continue;
                }
                if (red > 220 && greed > 180 && blue > 80) {
                    bi.setRGB(i, j, wColor.getRGB());
                }
                if (red <= 240 && greed >= 200 && blue >= 150) {
                    bi.setRGB(i, j, wColor.getRGB());
                }
            }
        }
    }
}
