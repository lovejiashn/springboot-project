package com.jiashn.springbootproject.word.service.impl;

import com.jiashn.springbootproject.word.service.ApachePoiService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlBoolean;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTLineProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.STPresetLineDashVal;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jiangjs
 * @date 2022-04-03 21:52
 */
@Service
public class ApachePoiServiceImpl implements ApachePoiService {
    private final static Logger log = LoggerFactory.getLogger(ApachePoiServiceImpl.class);

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

    @Override
    public void generatePic() {
        String filePath = "D:\\down\\month_report.docx";
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = new FileInputStream(new File(filePath));
            XWPFDocument doc = new XWPFDocument(is);
            //系列
            String[] seriesTitles = {"项目数量","投资额"};
            //X轴
            String[] categories = {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月"};
            List<Number[]> data = new ArrayList<>();
            //项目数量值
            Number[] proRates = {-11.02,-19.42,-10.61,-11.41,-7.91,-5.44,-5.30,-2.75,-1.24,0.35};
            Number[] moneyRates = {-12.66,-19.41,-15.16,-19.72,-17.05,-15.92,-15.10,-13.04,-10.65,-9.15};
            data.add(proRates);
            data.add(moneyRates);
            List<XWPFChart> charts = doc.getCharts();
            //生成折线图
            XWPFChart lineChart = CollectionUtils.isNotEmpty(charts) ? charts.get(0) : doc.createChart(16 * Units.EMU_PER_CENTIMETER,6 * Units.EMU_PER_CENTIMETER);
            generateLineChart(lineChart,seriesTitles,categories,data, AxisPosition.LEFT);
            //生成柱状图
            XWPFChart barChart = CollectionUtils.isNotEmpty(charts) && charts.size() > 1 ? charts.get(1) : doc.createChart(16 * Units.EMU_PER_CENTIMETER,8 * Units.EMU_PER_CENTIMETER);
            //系列
            String[] barTitles = {"开工数量","投资额"};
            String[] barCategories = {"贵州","西藏","黑龙江","浙江","湖北","江苏","四川","福建","安徽","海南","山西","广西","青海","广东","甘肃",
                    "云南","宁夏","新疆","湖南","北京","河北","山西","山东","内蒙古","天津","江西","吉林","河南","重庆","上海","辽宁"};
            Number[] openRates = {40,50,45,12,21,18,21,28,21,18,28,18,20,19,-10,-9,-10,19,39,31,20,19,-10,-9,-10,19,39,31,-10,19,39};
            Number[] barMoneyRates = {20,-22,-12,8,-10,-14,-10,-10,-8,-2,-8,-1,-9,-21,-9,-7,-21,-10,21,-29,-50,-21,-9,-7,-21,-10,21,-29,-21,-10,21};
            List<Number[]> barData = new ArrayList<>();
            barData.add(openRates);
            barData.add(barMoneyRates);
            generateBarChart(barChart,barTitles,barCategories,barData,BarDirection.COL,"各省（自治区）直辖市新开项目数量、投资额同比情况", AxisPosition.LEFT);

            //同比柱状图
            XWPFChart rateChart = CollectionUtils.isNotEmpty(charts) && charts.size() > 2 ? charts.get(2) : doc.createChart(16 * Units.EMU_PER_CENTIMETER,6 * Units.EMU_PER_CENTIMETER);
            //系列
            String[] rateTitles = {"2022年","2021年"};
            String[] rateCategories = {"从开工到验收的用时","从立项到开工的用时"};
            Number[] time = {400,220};
            Number[] acceptance = {456,255};
            List<Number[]> rateData = new ArrayList<>();
            rateData.add(time);
            rateData.add(acceptance);
            generateBarChart(rateChart,rateTitles,rateCategories,rateData,BarDirection.BAR,"工程建设项目建设周期同比情况", AxisPosition.LEFT);
            //各地市开工情况(折线+柱状图)
            XWPFChart openChart = CollectionUtils.isNotEmpty(charts) && charts.size() > 3 ? charts.get(3) : doc.createChart(16 * Units.EMU_PER_CENTIMETER,8 * Units.EMU_PER_CENTIMETER);
            String[] unOpenTitles = {"未开工项目数（个）","开工率（%）"};
            String[] openCategories = {"北京","吉林","云南","上海","安徽","浙江","江西","四川","陕西","甘肃","江苏","广西","内蒙古","福建","天津","海南","黑龙江",
                    "贵州","山东","河北","辽宁","湖北","宁夏","广东","重庆","河南","新疆","山西","湖南","青海","兵团"};
            Number[] unOpenRates = {55,35,23,76,60,65.1,70.2,75.3,80.4,85.5,90.6,95.7,26,76,60,65.1,70.2,75.3,80.4,95.7,26,76,60,65.1,70.2,75.3,95.7,26,76,60,65.1};
            Number[] startRates = {34,45,23,67,34,45,23,67,34,45,23,67,23,67,34,45,23,45,23,67,23,67,34,45,23,45,67,23,67,34,45};
            List<Number[]> unOpenData = new ArrayList<>();
            unOpenData.add(unOpenRates);
            unOpenData.add(startRates);
           /* List<Number[]> unOpenData = new ArrayList<>();
            List<Number[]> openData = new ArrayList<>();
            unOpenData.add(unOpenRates);
            openData.add(startRates);
            generateLineChart(openChart,openTitles,openCategories,openData,AxisPosition.RIGHT);
            generateBarChart(openChart,unOpenTitles,openCategories,unOpenData,BarDirection.COL,"各省（区、市）签约项目开工情况",AxisPosition.LEFT);*/
            generateGroupChart(openChart,unOpenTitles,openCategories,unOpenData,BarDirection.COL,"各省（区、市）签约项目开工情况", AxisPosition.LEFT);
            fos = new FileOutputStream(filePath);
            doc.write(fos);
        }catch (Exception e){
            log.error("word生成折线图报错：{}",e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (Objects.nonNull(fos)){
                    fos.close();
                }
                if (Objects.nonNull(is)){
                        is.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void generateGroupChart(HttpServletResponse response) {
        this.generateGroupCharts(response);
    }

    private void generateLineChart(XWPFChart chart, String[] series, String[] categories, List<Number[]> data, AxisPosition axisPosition){
        //设置标题
        this.installCharTitle(chart,"1-10月份全国新开工项目数量、投资额增速");
        //图例位置
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.BOTTOM);
        legend.setOverlay(Boolean.TRUE);
        //X轴属性
        XDDFCategoryAxis categoryAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        categoryAxis.setCrosses(AxisCrosses.MIN);
        //Y轴属性
        XDDFValueAxis leftAxis = chart.createValueAxis(axisPosition);
        leftAxis.setCrosses(AxisCrosses.MIN);
        //生成折线
        List<XDDFChartData> chartSeries = chart.getChartSeries();
        XDDFLineChartData lineChartData = CollectionUtils.isNotEmpty(chartSeries) ? (XDDFLineChartData)chartSeries.get(0):
                (XDDFLineChartData)chart.createData(ChartTypes.LINE, categoryAxis, leftAxis);
        int numOfPoints = categories.length;
        String categoryDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 0, 0));
        XDDFCategoryDataSource countries = XDDFDataSourcesFactory.fromArray(categories,categoryDataRange,0);
        //加载数据
        int num = 0;
        for (Number[] datum : data) {
            String valuesDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, num+1, num+1));
            XDDFNumericalDataSource<Number> value = XDDFDataSourcesFactory.fromArray(datum, valuesDataRange, num+1);
            XDDFLineChartData.Series ser = CollectionUtils.isNotEmpty(chartSeries) ? (XDDFLineChartData.Series) lineChartData.getSeries().get(num) :
                    (XDDFLineChartData.Series)lineChartData.addSeries(countries,value);
            CellReference reference = null;
            if (CollectionUtils.isNotEmpty(chartSeries)){
                ser.replaceData(countries, value);
                reference = chart.setSheetTitle(series[num], 1);
            }
            ser.setTitle(series[num],reference);
            ser.setMarkerSize((short)6);
            ser.setMarkerStyle(MarkerStyle.SQUARE);
            ser.setSmooth(Boolean.FALSE);
           // ser.setShowLeaderLines(Boolean.FALSE);
            num++;
        }
        chart.plot(lineChartData);
    }

    /**
     * 设置标题样式
     * @param chart 表格chart
     * @param title 标题
     */
    private void installCharTitle(XWPFChart chart,String title){
        CTTitle ctTitle = chart.getCTChart().getTitle();
        CTRegularTextRun textRun;
        if (Objects.isNull(ctTitle)){
            ctTitle = chart.getCTChart().addNewTitle();
            ctTitle.addNewOverlay().setVal(Boolean.FALSE);
            ctTitle.addNewTx().addNewRich().addNewBodyPr();
            CTTextBody rich = ctTitle.getTx().getRich();
            rich.addNewLstStyle();
            textRun = rich.addNewP().addNewR();
            textRun.addNewRPr().setB(false);
            XmlBoolean xmlBoolean = XmlBoolean.Factory.newInstance();
            xmlBoolean.setStringValue("0");
            textRun.getRPr().xsetB(xmlBoolean);
            textRun.getRPr().setLang("zh-CN");
            textRun.getRPr().setSz(1200);
        } else {
            ctTitle.getOverlay().setVal(Boolean.TRUE);
            ctTitle.getTx().getRich().getBodyPr();
            CTTextBody rich = ctTitle.getTx().getRich();
            rich.getLstStyle();
            textRun = rich.getPArray(0).getRArray(0);
        }
        textRun.setT(title);
        //标题覆盖
        chart.setTitleOverlay(Boolean.TRUE);
    }

    private void generateBarChart(XWPFChart chart, String[] series, String[] categories, List<Number[]> data, BarDirection direction, String title, AxisPosition axisPosition){
        //设置标题
        this.installCharTitle(chart,title);
        //图例位置
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.BOTTOM);
        legend.setOverlay(Boolean.TRUE);
        //X轴属性
        XDDFCategoryAxis xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        //Y轴属性
        XDDFValueAxis yAxis = chart.createValueAxis(axisPosition);
        // 设置图柱的位置:BETWEEN居中
        yAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
        //生成柱状图
        List<XDDFChartData> chartSeries = chart.getChartSeries();
        XDDFChartData chartData =  CollectionUtils.isNotEmpty(chartSeries) ? chartSeries.get(0) : chart.createData(ChartTypes.BAR, xAxis, yAxis);
        XDDFBarChartData barChartData = (XDDFBarChartData) chartData;
        barChartData.setBarDirection(direction);
        int numOfPoints = categories.length;
        String categoryDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 0, 0));
        XDDFCategoryDataSource countries = XDDFDataSourcesFactory.fromArray(categories,categoryDataRange);
        //加载数据
        int num = 0;
        for (Number[] datum : data) {
            String valuesDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, num+1, num+1));
            XDDFNumericalDataSource<Number> value = XDDFDataSourcesFactory.fromArray(datum, valuesDataRange, num+1);
            XDDFBarChartData.Series ser = CollectionUtils.isNotEmpty(chartSeries) ? (XDDFBarChartData.Series) barChartData.getSeries().get(num) :
                    (XDDFBarChartData.Series)barChartData.addSeries(countries,value);
            CellReference reference = null;
            if (CollectionUtils.isNotEmpty(chartSeries)){
                ser.replaceData(countries, value);
                reference = chart.setSheetTitle(series[num], 1);
            }
            ser.setTitle(series[num],reference);
           // ser.setShowLeaderLines(Boolean.FALSE);
            num++;
        }
        chart.plot(barChartData);
    }

    private void generateGroupChart(XWPFChart chart, String[] series, String[] categories, List<Number[]> data,
                                    BarDirection direction, String title, AxisPosition axisPosition){

        //设置标题
        this.installCharTitle(chart,title);
        //图例位置
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.BOTTOM);
        legend.setOverlay(Boolean.TRUE);
        //X轴属性
        XDDFCategoryAxis xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        //Y轴属性
        XDDFValueAxis yAxis = chart.createValueAxis(axisPosition);
        // 设置图柱的位置:BETWEEN居中
        yAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
        //生成柱状图
        List<XDDFChartData> chartSeries = chart.getChartSeries();
        int numOfPoints = categories.length;
        String valuesDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 1, 1));
        String lineDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 2, 2));
        String categoryDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 0, 0));
        XDDFCategoryDataSource countries = XDDFDataSourcesFactory.fromArray(categories,categoryDataRange);
        XDDFChartData chartData = chart.createData(ChartTypes.BAR, xAxis, yAxis);
        //生成柱状图
        ((XDDFBarChartData) chartData).setBarDirection(direction);
        //加载数据
        Number[] numbers = data.get(0);
        XDDFNumericalDataSource<Number> value = XDDFDataSourcesFactory.fromArray(numbers, valuesDataRange, 1);
        XDDFBarChartData.Series ser = (XDDFBarChartData.Series)chartData.addSeries(countries,value);
        CellReference reference = null;
        if (CollectionUtils.isNotEmpty(chartSeries)){
            ser.replaceData(countries, value);
            reference = chart.setSheetTitle(series[0], 1);
        }
        ser.setTitle(series[0],reference);
        chart.plot(chartData);
        //生成折线图
        chartData = chart.createData(ChartTypes.LINE, xAxis, yAxis);
        Number[] lines = data.get(1);
        //加载数据
        XDDFNumericalDataSource<Number> lineValue = XDDFDataSourcesFactory.fromArray(lines, lineDataRange, 2);
        XDDFLineChartData.Series lineSer = (XDDFLineChartData.Series) chartData.addSeries(countries,lineValue);
        CellReference lineReference = null;
        if (CollectionUtils.isNotEmpty(chartSeries)){
            lineSer.replaceData(countries, lineValue);
            lineReference = chart.setSheetTitle(series[1], 1);
        }
        lineSer.setTitle(series[1],lineReference);
        lineSer.setMarkerSize((short)2);
        lineSer.setMarkerStyle(MarkerStyle.CIRCLE);
        lineSer.setSmooth(Boolean.FALSE);
        chart.plot(chartData);
    }


    private void generateGroupCharts(HttpServletResponse response){
        //创建文本对象
        XWPFDocument document = new XWPFDocument();
        try {
            //共用X轴数据
            List<String> DateList = new ArrayList<>();
            DateList.add("2022-01-01");
            DateList.add("2022-01-02");
            DateList.add("2022-01-03");
            DateList.add("2022-01-04");
            DateList.add("2022-01-05");
            DateList.add("2022-01-06");
            String[] categories = {"2022-01-01","2022-01-02","2022-01-03","2022-01-04","2022-01-05","2022-01-06"};
            //柱状图Y轴数据
            List<Double> HistogramDataList = new ArrayList<>();
            HistogramDataList.add(1.74);
            HistogramDataList.add(2.31);
            HistogramDataList.add(0.65);
            HistogramDataList.add(1.42);
            HistogramDataList.add(2.00);
            HistogramDataList.add(1.73);
            Double[] HistogramvaluesA  ={1.74,2.31,0.65,1.42,2.00,1.73};
            //折线图Y轴数据
            List<Double> LineDataList = new ArrayList<>();
            LineDataList.add(1.74);
            LineDataList.add(2.31);
            LineDataList.add(0.65);
            LineDataList.add(1.42);
            LineDataList.add(2.00);
            LineDataList.add(1.73);
            Double[] LinevaluesA = {1.74,2.31,0.65,1.42,2.00,1.73} ;
//-----------------------------------------柱状图-------------------------------------------------
            // 设置图表大小
            XWPFChart chart = document.createChart(15 * Units.EMU_PER_CENTIMETER, 5 * Units.EMU_PER_CENTIMETER);
            //创建相关数据
            int numOfPoints = categories.length;
            String categoryDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 0, 0));
            String valuesDataRangeA = chart.formatRange(new CellRangeAddress(1, numOfPoints, 1, 1));
            String lineDataRangeA = chart.formatRange(new CellRangeAddress(1, numOfPoints, 2, 2));
            XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromArray(categories, categoryDataRange, 1);
            XDDFNumericalDataSource<Double> valuesDataA = XDDFDataSourcesFactory.fromArray(HistogramvaluesA, valuesDataRangeA, 1);
            //创建X轴
            XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.TOP);
            // 左Y轴
            XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
            // 左Y轴和X轴交叉点在X轴0点位置，在这里我直接注释掉了。
//          leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
//          leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
            // 构建坐标轴
            leftAxis.crossAxis(bottomAxis);
            bottomAxis.crossAxis(leftAxis);
            //设置柱状图Y轴名称，方位和坐标轴大小
            leftAxis.setTitle("降雨量/mm");
            leftAxis.setCrosses(AxisCrosses.MAX);
            leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
            // create series
            bottomAxis.setMajorTickMark(AxisTickMark.NONE);//取消X轴的标刻度
            //获取X轴 图表的基本配置都在这个对象里面里面
            CTCatAx catAx = chart.getCTChart().getPlotArea().getCatAxArray(0);
           /* CTSkip ctSkip = CTSkip.Factory.newInstance();
            //设置显示间隔
            ctSkip.setVal((int) Math.ceil(1));
            catAx.setTickLblSkip(ctSkip);*/
            //设置标签位置为最下
            CTTickLblPos ctTickLblPos = CTTickLblPos.Factory.newInstance();
            ctTickLblPos.setVal(STTickLblPos.LOW);
            catAx.setTickLblPos(ctTickLblPos);
            //获取Y轴 图表的基本配置都在这个对象里面里面
            CTValAx catAy = chart.getCTChart().getPlotArea().getValAxArray(0);
            CTScaling ctScaling ;
            ctScaling = catAy.addNewScaling();
            //设置柱状图Y轴坐标最大值
            ctScaling.addNewMax().setVal(8);

            ctScaling.addNewOrientation().setVal(STOrientation.MAX_MIN);
            catAy.setScaling(ctScaling);
            // 设置图表背后的网格线
            CTLineProperties ctLine = catAy.addNewMajorGridlines().addNewSpPr().addNewLn();
            ctLine.addNewPrstDash().setVal(STPresetLineDashVal.DASH);

            //创建柱状图数据对象
            XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
            ((XDDFBarChartData) data).setBarDirection(BarDirection.COL);
            //柱状图图例标题
            XDDFChartData.Series series = data.addSeries(categoriesData, valuesDataA);
            series.setTitle("下雨量", setTitleInDataSheet(chart, "", 0));
            chart.plot(data);
//-----------------------------------------折线图-------------------------------------------------
            // 右Y轴
            XDDFValueAxis rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
            // 右Y轴和X轴交叉点在X轴最大值位置
            rightAxis.setCrosses(AxisCrosses.MIN);
            rightAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
            // 构建坐标轴
            rightAxis.crossAxis(bottomAxis);
            bottomAxis.crossAxis(rightAxis);
            //设置折线图Y轴名称
            rightAxis.setTitle("水位/m");
            XDDFCategoryDataSource countries = XDDFDataSourcesFactory.fromArray(categories,categoryDataRange,0);
            //设置折线图Y轴坐标最大值
            rightAxis.setMaximum(8);
            //LINE：折线图，
            data = chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
            //加载折线图数据
            XDDFNumericalDataSource<Double> area = XDDFDataSourcesFactory.fromArray(LinevaluesA,lineDataRangeA,0);
            //图表加载数据，折线1
            XDDFLineChartData.Series series1 = (XDDFLineChartData.Series) data.addSeries(countries, area);
            //折线图例标题
            series1.setTitle("水位", null);
            //直线
            series1.setSmooth(true);
            //设置标记大小
            series1.setMarkerSize((short) 2);
            //设置空数据显示间隙
            CTDispBlanksAs disp = CTDispBlanksAs.Factory.newInstance();
            disp.setVal(STDispBlanksAs.GAP);
            chart.getCTChart().setDispBlanksAs(disp);
            data.setVaryColors(false);
            //绘制
            chart.plot(data);
            //设置图表图例
            XDDFChartLegend legend = chart.getOrAddLegend();
            legend.setPosition(LegendPosition.TOP);
            //生成word文件，设置文件相关信息。
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("折线+柱状组合图.docx", "UTF-8"));
            OutputStream out = response.getOutputStream();
            document.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CellReference setTitleInDataSheet(XWPFChart chart, String title, int column) {
        try {
            XSSFWorkbook workbook = null;
            workbook = chart.getWorkbook();
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(0);
            if (row == null) {
                row = sheet.createRow(0);
            }
            XSSFCell cell = row.getCell(column);
            if (cell == null) {
                cell = row.createCell(column);
            }
            cell.setCellValue(title);
            return new CellReference(sheet.getSheetName(), 0, column, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
