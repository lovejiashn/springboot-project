package com.jiashn.springbootproject.word.realize;

import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.jiashn.springbootproject.word.domain.ChartSeriesRenderData;
import com.jiashn.springbootproject.word.domain.LabelData;
import com.jiashn.springbootproject.word.enums.WordContentTypeEnum;
import com.jiashn.springbootproject.word.factory.GenerateWordFactory;
import com.jiashn.springbootproject.word.service.GenerateWord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 图表类型
 * @date: 2022/11/24 14:32
 **/
@Component
public class ChartGenerateWord implements GenerateWord {
    @PostConstruct
    private void init(){
        GenerateWordFactory.register(WordContentTypeEnum.CHART,this);
    }
    @Override
    public Object generateWord(LabelData obj) {
        ChartMultiSeriesRenderData seriesRenderData = new ChartMultiSeriesRenderData();
        ChartSeriesRenderData renderData  = (ChartSeriesRenderData) obj;
        seriesRenderData.setCategories(renderData.getCategories());
        seriesRenderData.setChartTitle(renderData.getTitle());
        List<ChartSeriesRenderData.RenderData> renderDataList = renderData.getSenderData();
        List<SeriesRenderData> groupData = new ArrayList<>();
        renderDataList.forEach(data -> {
            SeriesRenderData srd = new SeriesRenderData(data.getRenderTitle(),data.getData());
            if (Objects.nonNull(data.getComboType())){
                srd.setComboType(data.getComboType());
            }
            groupData.add(srd);
        });
        seriesRenderData.setSeriesDatas(groupData);
        return seriesRenderData;
    }
}