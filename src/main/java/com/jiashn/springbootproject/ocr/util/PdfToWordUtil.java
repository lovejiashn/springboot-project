package com.jiashn.springbootproject.ocr.util;

import com.jiashn.springbootproject.ocr.domain.WordContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/10/30 15:55
 **/
@Component
@Slf4j
public class PdfToWordUtil {
    @Resource
    private RapidOcrUtil rapidocrUtil;
    public List<WordContent> pdfToWord(String pdfPath){
        File file = new File(pdfPath);
        List<WordContent> pdfContents = new ArrayList<>();
        FileOutputStream out = null;
        PDDocument document = null;
        try{
            document = PDDocument.load(file);
            int pageNum = document.getNumberOfPages();
            for (int i = 1; i <= pageNum; i++) {
                PDFTextStripper stripper = new PDFTextStripper();
                //设置输出顺序
                stripper.setSortByPosition(true);
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                String content = stripper.getText(document);
                WordContent wordContent = new WordContent().setPageNum(i).setWordContent(content.trim());
                //图片内容
                PDPage page = document.getPage(i-1);
                PDResources resources = page.getResources();
                Iterable<COSName> cosNames = resources.getXObjectNames();
                if (Objects.nonNull(cosNames)){
                    Iterator<COSName> iterator = cosNames.iterator();
                    List<String> filePaths = new ArrayList<>();
                    while (iterator.hasNext()){
                        COSName cosName = iterator.next();
                        if (resources.isImageXObject(cosName)){
                            String imagePath = "D:\\image\\" + UUID.randomUUID() + ".png";
                            PDImageXObject Ipdmage = (PDImageXObject) resources.getXObject(cosName);
                            BufferedImage image = Ipdmage.getImage();
                            out = new FileOutputStream(imagePath);
                            ImageIO.write(image, "png", out);
                            filePaths.add(imagePath);
                        }
                    }
                    if (CollectionUtils.isNotEmpty(filePaths)){
                        List<String> words = new ArrayList<>();
                        for (String path : filePaths) {
                            String ocrResult = rapidocrUtil.getOcrResult(path);
                            if (StringUtils.isNoneBlank(ocrResult)){
                                words.add(ocrResult);
                            }
                            File saveFile = new File(path);
                            saveFile.delete();
                        }
                        wordContent.setImageContents(words);
                    }
                }
                pdfContents.add(wordContent);
            }
            
        }catch (Exception e){
            log.error("pdf转word报错:{}",e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (Objects.nonNull(document)){
                    document.close();
                }
                if (Objects.nonNull(out)){
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return pdfContents;
    }
}
