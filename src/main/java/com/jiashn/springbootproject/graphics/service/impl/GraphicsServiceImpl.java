package com.jiashn.springbootproject.graphics.service.impl;

import com.jiashn.springbootproject.graphics.service.GraphicsService;
import com.jiashn.springbootproject.graphics.utils.GraphicsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/5/24 16:02
 **/
@Service
public class GraphicsServiceImpl implements GraphicsService {
    @Autowired
    private GraphicsUtil graphicsUtil;

    @Override
    public void getSealImage(HttpServletResponse response) {
        BufferedImage bufferedImage = graphicsUtil.getSeal("甘州区住房和城乡建设局","★","",160,160);
        response.setHeader("Content-Type","jpeg");
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(bufferedImage,"png",out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(out)){
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}