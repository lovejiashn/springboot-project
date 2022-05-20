package com.jiashn.springbootproject.word.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.word.Word07Writer;
import com.jiashn.springbootproject.word.service.HutoolService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

/**
 * @author jiangjs
 * @date 2022-04-03 20:14
 */
@Service
public class HutoolServiceImpl implements HutoolService {
    @Override
    public void getWordByHutool() {
        Word07Writer w = new Word07Writer();
        // 添加段落（标题）
        w.addText(new Font("方正小标宋简体", Font.BOLD, 12), "我是第一部分", "我是第二部分");
        // 添加段落（正文）
        w.addText(new Font("宋体", Font.PLAIN, 16), "  我是正文第一部分", "我是正文第二部分");
        // 写出到文件
        w.flush(FileUtil.file("e:/wordWrite.docx"));
        // 关闭
        w.close();
    }


    public void downLoadWord01(HttpServletResponse response) {
        Word07Writer writer = new Word07Writer();
        // 添加段落（标题）
        writer.addText(new Font("方正小标宋简体", Font.BOLD, 12), "我是第一部分", "我是第二部分");
        // 添加段落（正文）
        writer.addText(new Font("宋体", Font.PLAIN, 16), "  我是正文第一部分", "我是正文第二部分");
        // 写出到文件
        ServletOutputStream os = null;
        try {
            os = response.getOutputStream();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            String fileName = "下载文件.docx";
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            writer.flush(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(os)){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        writer.close();
    }

    @Override
    public void downLoadWord(HttpServletResponse response) {
        Word07Writer writer = new Word07Writer();
        List<List<String>> rel = new ArrayList<>();
        List<String> title = Arrays.asList("建设单位","工程名称","建设地址","建设规模");
        List<String> values = Arrays.asList("广州奥格","20","广州黄村","1600人");
        rel.add(title);
        rel.add(values);
        //加表格
        writer.addTable(rel);
        // 添加段落（标题）
        writer.addText(new Font("方正小标宋简体", Font.BOLD, 12), "我是第一部分", "我是第二部分");
        // 添加段落（正文）
        writer.addText(new Font("宋体", Font.PLAIN, 16), "  我是正文第一部分", "我是正文第二部分");
        // 写出到文件
        ServletOutputStream os = null;
        try {
            os = response.getOutputStream();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            String fileName = "下载文件.docx";
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            writer.flush(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(os)){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        writer.close();
    }
}
