package com.jiashn.springbootproject.word.realize;

import com.jiashn.springbootproject.word.domain.LabelData;
import com.jiashn.springbootproject.word.domain.TextContentData;
import com.jiashn.springbootproject.word.enums.WordContentTypeEnum;
import com.jiashn.springbootproject.word.factory.GenerateWordFactory;
import com.jiashn.springbootproject.word.service.GenerateWord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: jiangjs
 * @description: 文本内容实现
 * @date: 2022/11/24 14:28
 **/
@Component
public class TextGenerateWord implements GenerateWord {

    @PostConstruct
    public void init(){
        GenerateWordFactory.register(WordContentTypeEnum.TEXT,this);
    }

    @Override
    public Object generateWord(LabelData data) {
        TextContentData contentData = (TextContentData) data;
        return contentData.getContent();
    }
}