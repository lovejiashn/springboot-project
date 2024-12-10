package com.jiashn.springbootproject.ocr.util;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: jiangjs
 * @description: 图片转换成pdf
 * @date: 2024/11/5 14:01
 **/
@Component
public class ImageToPDFUtil {
    public void imageToPDF(String imagePath) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        File file = new File(imagePath);
        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, FileUtils.readFileToByteArray(file), file.getName());
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(pdImage,0,0);
        contentStream.close();
        String outPath = "D:\\image\\"+ UUID.randomUUID() +".pdf";
        document.save(outPath);
        document.close();
    }
}
