package com.jiashn.springbootproject.graphics.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author: jiangjs
 * @description: Graphics操作图片
 * @date: 2022/5/24 14:02
 **/
@Component
public class GraphicsUtil {

    /**
     * 生成电子签章
     * @param head 头部信息
     * @param center 中间部分信息
     * @param foot 底部信息
     * @param canvasWidth 签章宽度
     * @param canvasHeight 签章高度
     * @return 返回图片流
     */
    public BufferedImage getSeal(String head,String center,String foot,int canvasWidth,int canvasHeight){
        //设置图片
        BufferedImage image = new BufferedImage(canvasWidth,canvasHeight,BufferedImage.TYPE_INT_RGB);
        //1、创建Graphics2D对象
        Graphics2D graphics2D = image.createGraphics();
        //设置透明度， 1.0f为透明度 ，值从0-1.0，依次变得不透明
        //graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));
        graphics2D.getDeviceConfiguration().createCompatibleImage(canvasWidth,canvasHeight,Transparency.TRANSLUCENT);
        //2、填充
        //2.1 设置画笔
        //设置画笔颜色
        graphics2D.setPaint(new Color(255,255,255));
        graphics2D.setStroke(new BasicStroke(0f));
        //填充区域，将区域背景颜色设置为白色
        graphics2D.fillRect(0,0,canvasWidth,canvasHeight);
        //3.画圆
        int circleRadius = Math.min(canvasWidth,canvasHeight) / 2;
        //设置Shape（形状）对象
        graphics2D.setPaint(Color.red);
        //设置画笔的粗度
        graphics2D.setStroke(new BasicStroke(5));
        Shape circle = new Arc2D.Double(0, 0, circleRadius * 2, circleRadius * 2, 0, 360, Arc2D.OPEN);
        graphics2D.draw(circle);
        //4.写字
        //4.1 设置中间部分字体
        Font font = new Font("黑体", Font.PLAIN, 50);
        FontRenderContext renderContext = graphics2D.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(center, renderContext);
        graphics2D.setFont(font);
        graphics2D.drawString(center,(float) (circleRadius - bounds.getCenterX()), (float) (circleRadius - bounds.getCenterY()));
        //4.2 设置底部文字
        if (StringUtils.isNotBlank(foot)){
            font = new Font("黑体",Font.BOLD,50);
            renderContext = graphics2D.getFontRenderContext();
            bounds = font.getStringBounds(foot, renderContext);
            graphics2D.setFont(font);
            graphics2D.drawString(foot, (float) (circleRadius - bounds.getCenterX()), (float) (circleRadius*1.5 - bounds.getCenterY()));
        }
        //4.3 设置章子弧形文字
        font = new Font("黑体",Font.PLAIN,24);
        renderContext = graphics2D.getFontRenderContext();
        bounds = font.getStringBounds(head, renderContext);
        double width = bounds.getWidth();
        int length = head.length();
        //文字间距
        double interval = width / (length - 1);
        //bounds.getY()是负数，这样可以将弧形文字固定在圆内了。-5目的是离圆环稍远一点
        double newRadius = circleRadius + bounds.getY() - 5;
        //每个间距对应的角度
        double radianPerInterval = 2 * Math.asin(interval / (2 * newRadius));
        //设置第一个文字的位置
        double firstAngle;
        if (length % 2 == 1){
            firstAngle = (length-1)*radianPerInterval/2.0 + Math.PI/2+0.08;
        }else {
            firstAngle = (length/2.0-1)*radianPerInterval + radianPerInterval/2.0 +Math.PI/2+0.08;
        }
        for (int i = 0; i < length; i++) {
            double aa = firstAngle - i*radianPerInterval;
            //小小的trick，将【0，pi】区间变换到[pi/2,-pi/2]区间
            double ax = newRadius * Math.sin(Math.PI/2 - aa);
            //同上类似，这样处理就不必再考虑正负的问题了
            double ay = newRadius * Math.cos(aa-Math.PI/2);
            // ,x0 + ax, y0 + ay);
            AffineTransform transform = AffineTransform .getRotateInstance(Math.PI/2 - aa);
            Font f2 = font.deriveFont(transform);
            graphics2D.setFont(f2);
            graphics2D.drawString(head.substring(i,i+1), (float) (circleRadius+ax),  (float) (circleRadius - ay));
        }
        graphics2D.dispose();
        return image;
    }
}