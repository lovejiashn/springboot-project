package com.jiashn.springbootproject.word.realize;

import com.deepoove.poi.data.NumbericRenderData;
import com.jiashn.springbootproject.word.domain.LabelData;
import com.jiashn.springbootproject.word.domain.ListRenderData;
import com.jiashn.springbootproject.word.enums.WordContentTypeEnum;
import com.jiashn.springbootproject.word.factory.GenerateWordFactory;
import com.jiashn.springbootproject.word.service.GenerateWord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/6/2 15:42
 **/
@Component
public class ListGenerateWord implements GenerateWord {
    @PostConstruct
    private void init(){
        GenerateWordFactory.register(WordContentTypeEnum.LIST,this);
    }
    @Override
    public Object generateWord(LabelData data) {
        ListRenderData listData =  (ListRenderData) data;
        return new NumbericRenderData(listData.getPair(),listData.getList());
    }
}
