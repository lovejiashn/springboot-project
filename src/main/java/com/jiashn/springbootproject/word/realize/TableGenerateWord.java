package com.jiashn.springbootproject.word.realize;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.jiashn.springbootproject.word.domain.LabelData;
import com.jiashn.springbootproject.word.domain.TableSeriesRenderData;
import com.jiashn.springbootproject.word.enums.WordContentTypeEnum;
import com.jiashn.springbootproject.word.factory.GenerateWordFactory;
import com.jiashn.springbootproject.word.service.GenerateWord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/11/24 17:20
 **/
@Component
public class TableGenerateWord implements GenerateWord {
    @PostConstruct
    private void init(){
        GenerateWordFactory.register(WordContentTypeEnum.TABLE,this);
    }
    @Override
    public Object generateWord(LabelData data) {
        TableSeriesRenderData tableData = (TableSeriesRenderData) data;
        RowRenderData header = RowRenderData.build(tableData.getHeader());
        List<RowRenderData> contentData = new ArrayList<>();
        tableData.getContents().forEach(con ->{
            contentData.add(RowRenderData.build(con));
        });
        return new MiniTableRenderData(header,contentData);
    }
}