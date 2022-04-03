package com.jiashn.springbootproject.word.service.impl;

import com.jiashn.springbootproject.word.service.ApachePoiService;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author jiangjs
 * @date 2022-04-03 21:52
 */
@Service
public class ApachePoiServiceImpl implements ApachePoiService {
    @Override
    public void downLoadWord(HttpServletResponse response) {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(18);
        run.setText("这是测试文件");
        run.setColor("FF0000");
        XWPFParagraph paragraph2 = document.createParagraph();
        XWPFRun run2 = paragraph2.createRun();
        run2.setText("22222222222");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            document.write(baos);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            String fileName = "下载文件.docx";
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            os.write(baos.toByteArray());
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
