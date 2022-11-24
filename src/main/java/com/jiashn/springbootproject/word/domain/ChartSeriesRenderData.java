package com.jiashn.springbootproject.word.domain;

import com.deepoove.poi.data.SeriesRenderData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: jiangjs
 * @description: 图表
 * @date: 2022/11/24 11:38
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ChartSeriesRenderData extends LabelData {
    /**
     * 横轴数据
     */
    private String[] categories;

    /**
     * 图表名称
     */
    private String title;

    /**
     * 系列对应数据
     */
    private List<RenderData> senderData;

    @Data
    public static class RenderData{
        /**
         * 系列名称
         */
        private String renderTitle;
        /**
         * 系列对应的数据
         */
        private Number[] data;
        /**
         * 该系列对应生成的图表类型
         */
        private SeriesRenderData.ComboType comboType = null;
    }
}