package com.jiashn.springbootproject.word.util;

import com.deepoove.poi.XWPFTemplate;
import com.jiashn.springbootproject.word.domain.LabelData;
import com.jiashn.springbootproject.word.factory.GenerateWordFactory;
import com.jiashn.springbootproject.word.service.GenerateWord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.util.*;

/**
 * @author: jiangjs
 * @description: 操作word内容
 * @date: 2022/11/24 11:32
 **/
public class OperateWordManage {
    private final static Logger log = LoggerFactory.getLogger(OperateWordManage.class);

    public static void generateWordContent(String tempFilePath, String destFilePath,List<LabelData> contents){
        FileOutputStream fos = null;
        XWPFTemplate template = null;
        try {
            template = XWPFTemplate.compile(tempFilePath).render(new HashMap<String,Object>(contents.size()){{
                contents.forEach(content ->{
                    GenerateWord backData = GenerateWordFactory.getBackData(content.getTypeEnum());
                    put(content.getLabelName(),backData.generateWord(content));
                });
            }});
            fos = new FileOutputStream(destFilePath);
            template.write(fos);
            fos.flush();
        }catch (Exception e){
            log.error("替换生成图表报错：{}",e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                if (Objects.nonNull(fos)){
                    fos.close();
                }
                if (Objects.nonNull(template)){
                    template.close();
                }
            }catch (Exception e){
                log.error("关闭数据流报错：{}",e.getMessage());
                e.printStackTrace();
            }
        }
    }
}