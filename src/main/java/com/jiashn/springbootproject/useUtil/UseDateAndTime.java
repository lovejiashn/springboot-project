package com.jiashn.springbootproject.useUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 使用LocalDate,LocalDateTime,Time
 * @date: 2023/5/11 11:06
 **/
public class UseDateAndTime {
    private static final DateTimeFormatter TIME_DTF = DateTimeFormatter.ofPattern("hh:mm:ss");
    private static final DateTimeFormatter DATE_DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public static void main(String[] args) {
        //获取当前日期
        LocalDate nowDate = LocalDate.now();
        System.out.println("获取当前日期："+nowDate);
        //获取当前时间
        LocalTime nowTime = LocalTime.now();
        System.out.println("获取当前时间："+nowTime);
        //获取当前日期时间
        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println("获取当前日期时间："+nowDateTime);
        //格式化
        System.out.println("日期格式化:" + DATE_DTF.format(nowDate));
        System.out.println("时间格式化:" + TIME_DTF.format(nowTime));
        System.out.println("日期时间格式化:" + DATE_TIME_DTF.format(nowDateTime));
/*
        System.out.println("日期样式:" + DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(nowDate));
        System.out.println("日期样式:" + DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(nowDate));
        System.out.println("日期样式:" + DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(nowDate));
        System.out.println("日期样式:" + DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(nowDate));

        System.out.println("时间样式:" + DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL).format(LocalTime.now()));
        System.out.println("时间样式:" + nowTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
       // System.out.println("时间样式:" + nowTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG)));
        System.out.println("时间样式:" + nowTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));

        System.out.println("日期时间样式:" + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(nowDateTime));*/

        LocalDate ofDate = LocalDate.of(2022, Month.JANUARY, 31);
        System.out.println("of方法转LocalDate；" + ofDate);
        //String转LocalDate，LocalTime，LocalDateTime
        String date = "2020-01-01";
        String time = "11:22:33";
        String dateTime = "2021-09-06T09:51:51";
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime= LocalTime.parse(time);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
        System.out.println("String转LocalDate，LocalTime，LocalDateTime，LocalDate；" +
                localDate + "，LocalTime:" + localTime + "，LocalDateTime："+localDateTime);

        //date转LocalDate，LocalTime，LocalDateTime
        Date date1 = new Date();
        LocalDate localDate1 = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
        LocalTime localTime1 = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault()).toLocalTime();
        System.out.println("date转LocalDate，LocalTime，LocalDateTime，LocalDate；" +
                localDate1 + "，LocalTime:" + localTime1 + "，LocalDateTime："+localDateTime1);

        //LocalDateTime转Date
        Date from = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("LocalDateTime转Date；" + from );

        //获取年月日
        int year = nowDate.getYear();
        int monthValue = nowDate.getMonthValue();
        int month = nowDate.getMonth().getValue();
        int day = nowDate.getDayOfMonth();
        int dayOfYear = nowDate.getDayOfYear();
        int dayOfWeek = nowDate.getDayOfWeek().getValue();

        System.out.println("year：" + year + "，monthValue：" + monthValue +
                "，month：" + month + "，day：" + day +
                "，dayOfYear：" + dayOfYear + "，dayOfWeek：" + dayOfWeek );

        //时间比较
        LocalDate firDate = LocalDate.of(2022, 11, 23);
        LocalDate secDate = LocalDate.of(2023, 11, 23);
        LocalDate thrDate = LocalDate.of(2021, 11, 23);
        //equals
        System.out.println("equals：" + firDate.equals(secDate));
        //compareTo
        System.out.println("compareTo1：" + secDate.compareTo(thrDate));
        System.out.println("compareTo2：" + thrDate.compareTo(firDate));
        //isAfter
        System.out.println("isAfter：" + firDate.isAfter(secDate));
        System.out.println("isAfter：" + secDate.isAfter(thrDate));
        //isBefore
        System.out.println("isBefore：" + firDate.isBefore(secDate));
        System.out.println("isBefore：" + secDate.isBefore(thrDate));
        //周期性检查
        LocalDate birthDay = LocalDate.of(1991, 5, 11);
        MonthDay birthMonth = MonthDay.of(birthDay.getMonth(), birthDay.getDayOfMonth());
        MonthDay nowMonth = MonthDay.from(nowDate);
        if (Objects.equals(birthMonth,nowMonth)){
            System.out.println("生日到了！");
        } else {
            System.out.println("万幸，还好生日还没到呢，差点忘了......！");
        }
        //日期计算
        //提前
        System.out.println("两个月前的日期：" +  nowDate.minusMonths(2));
        System.out.println("20天前的日期：" + nowDate.minusDays(20));
        System.out.println("1星期前的日期：" + nowDate.minusWeeks(1));
        System.out.println("1年前的日期：" + nowDate.minusYears(1));
        System.out.println("1个月前的日期：" + nowDate.minus(1, ChronoUnit.MONTHS));
        //推后
        System.out.println("两个月后的日期：" + nowDate.plusMonths(2));
        System.out.println("20天后的日期：" + nowDate.plusDays(20));
        System.out.println("1星期后的日期：" + nowDate.plusWeeks(1));
        System.out.println("1年后的日期：" + nowDate.plusYears(1));
        System.out.println("1个月后的日期：" + nowDate.plus(1,ChronoUnit.MONTHS));

        //时区
        ZonedDateTime of = ZonedDateTime.of(nowDateTime, ZoneId.of("Asia/Shanghai"));
        System.out.println("时区：" + of);
        //时间戳
        System.out.println("时间戳：" + Instant.now().getEpochSecond());
        System.out.println("时间戳2：" + System.currentTimeMillis());

        //闰年
        boolean leapYear = nowDate.isLeapYear();
        if (leapYear){
            System.out.println("闰年！");
        } else {
            System.out.println("不是闰年！");
        }

        //获取当月第一天和最后一天
        LocalDate lastDay = nowDate.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate firstDay = nowDate.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println("当月第一天："+firstDay + "；当月最后一天：" + lastDay);
        //获取当年第一天和最后一天
        LocalDate lastYearDay = nowDate.with(TemporalAdjusters.lastDayOfYear());
        LocalDate firstYearDay = nowDate.with(TemporalAdjusters.firstDayOfYear());
        System.out.println("当年第一天："+firstYearDay + "；当月最后一天：" + lastYearDay);

    }
}
