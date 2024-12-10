package com.jiashn.springbootproject.useUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: jiangjs
 * @description: 使用StringUtils
 * @date: 2022/7/12 10:57
 **/
public class UseStringUtils {
    public static void main(String[] args) {
        //字符串分割
        String names = "张三,李四,王五,赵六";
        String[] nameVal = StringUtils.split(names, ",");
        System.out.println("分割字符:" + Arrays.toString(nameVal));

        //判断是否纯数字
        String num = "12345";
        String noNum = "12345a";
        System.out.println("纯数字:"+StringUtils.isNumeric(num) + ";非纯数字:"+StringUtils.isNumeric(noNum));

        //集合字符串拼接
        List<String> stringValues = new ArrayList<>();
        stringValues.add("我");
        stringValues.add("拼接");
        stringValues.add("起来啦");
        System.out.println("字符串拼接："+StringUtils.join(stringValues,""));

        //去掉字符串两端空格
        String name = "   中国No1  ";
        System.out.println("去空格："+StringUtils.trim(name) + "；null转换："+ StringUtils.trimToNull(null)
                + "：null转空值："+StringUtils.trimToEmpty(null));

        //包含
        String one = "jiashn love queena";
        String two = "jxs";
        String three = "小千s";
        System.out.println("包含任意："+StringUtils.containsAny(one,two) + "；不包含：" +
                StringUtils.containsNone(one,three));

        //提取字符串
        System.out.println("提取字符串(左边)："+StringUtils.left(one,6) + "；提取字符串(右边)：" +
                StringUtils.right(one,6));
        //字符出现次数
        System.out.println(Arrays.toString(one.split("e")));
        System.out.println(Arrays.toString(StringUtils.split(one,"e")));
        int count = one.split("e").length - 1;
        System.out.println("字符出现次数："+StringUtils.countMatches(one,"e") + "；字符串出现次数："+count);

        //是否全部不包含空值或空格
        System.out.println("是否全部不包含空值或空格："+StringUtils.isNoneBlank(one,three));

        //截取超过长度字段,超过部分并用...代替(获取字符长度=n-3(即点号的个数))
        System.out.println("截取超过长度字段："+StringUtils.abbreviate(one,6));
        //中间部分字符串替代
        System.out.println("中间截取长度字段："+StringUtils.abbreviateMiddle("13501544929","***",10));
        //是否以某字段结尾，否则添加，是则直接返回字符串
        System.out.println("是否以某字段结尾，否则添加，是则直接返回字符串："+StringUtils.appendIfMissing("jiashn",".com"));
        //首字母大写
        System.out.println("首字母大写："+StringUtils.capitalize("jiashn"));
        //字符串两边添加特殊字符
        System.out.println("字符串两边添加特殊字符："+StringUtils.center("jiashn",10,'*'));
        //字符左边添加特殊字符
        System.out.println("字符左边添加特殊字符："+StringUtils.leftPad("jiashn",10,'*'));
        //字符右边添加特殊字符
        System.out.println("字符右边添加特殊字符："+StringUtils.rightPad("jiashn",10,'*'));
        //左边字符串中的每一个字符必须出现在右边字符串中
        System.out.println("左边字符串中的每一个字符必须出现在右边字符串中："+StringUtils.containsOnly("xx","jiashn"));
        System.out.println("左边字符串中的每一个字符必须出现在右边字符串中："+StringUtils.containsOnly("xxx",'a','x'));
        //检测字符串中是否包含空白，包含回车和换行等空白符号
        System.out.println("检测字符串中是否包含空白："+StringUtils.containsWhitespace("jias\nhn"));
        //如果字符串为空白，则用一个默认字符串代替
        System.out.println("如果字符串为空白，则用一个默认字符串代替："+StringUtils.defaultIfBlank(" ", "jiashn"));
        System.out.println("如果字符串为空白，则用一个默认字符串代替："+StringUtils.defaultIfEmpty(" ", "456"));
        System.out.println("如果字符串为空白，则用一个默认字符串代替："+StringUtils.defaultString(null, "322"));
        //删除字符串中的空白
        System.out.println("删除字符串中的空白："+StringUtils.deleteWhitespace(one));
        //去除参数2字符串中与参数1字符串中相同的部分
        System.out.println("去除参数2字符串中与参数1字符串中相同的部分："+StringUtils.difference("jia", "jiashn"));
        //字符串是否以指定的一个或者多个字符串中的其中一个结尾
        System.out.println("字符串是否以指定的一个或者多个字符串中的其中一个结尾："+StringUtils.endsWithAny("jiashn", "xxx","hn"));
        //指定多个对比的字符串，有一个与主字符串相等即可
        System.out.println("指定多个对比的字符串，有一个与主字符串相等即可："+StringUtils.equalsAny("jiashn", "xxx","jiashn"));
        //获得一组字符串中第一个非空白的字符
        System.out.println("获得一组字符串中第一个非空白的字符："+StringUtils.firstNonBlank(" ", "jiashn"));
        System.out.println("获得一组字符串中第一个非空白的字符："+StringUtils.firstNonEmpty(" ", "jiashn"));
        //获得一组字符串公共部分的前缀
        System.out.println("获得一组字符串公共部分的前缀："+StringUtils.getCommonPrefix("jiashn", "jia","ja"));
        //提取字符串中数值部分
        System.out.println("提取字符串中数值部分："+StringUtils.getDigits("jiashn123"));
        //截取字符串左侧指定长度的字符串
        System.out.println("截取字符串左侧指定长度的字符串："+StringUtils.left("jiashn",3));
        //截取字符串右侧指定长度的字符串
        System.out.println("截取字符串右侧指定长度的字符串："+StringUtils.right("jiashn",3));
        //截取字符串某一部分
        System.out.println("截取字符串某一部分："+StringUtils.mid("jiashn.com",0,3));
        //覆盖字符串中指定位置的子串
        System.out.println("覆盖字符串中指定位置的子串："+StringUtils.overlay("13401892910", "****", 3, 7));
        //将字符串最后的指定个数字符提前
        System.out.println("将字符串最后的指定个数字符提前："+StringUtils.rotate("jiashn",2));
        //去除字符串两边的字符串，要去除的字符串可自己设定
        System.out.println("去除字符串左边的字符串："+StringUtils.strip("jiashn", "jn"));
        //批量去除两侧指定的字符串
        System.out.println("批量去除两侧指定的字符串："+ Arrays.toString(StringUtils.stripAll(new String[]{"jiashn", "jjjasnn"}, "jn")));
       //获取最后一个分隔符之后的字符串
        System.out.println("获取最后一个分隔符之后的字符串："+StringUtils.substringAfterLast("jia.shn.com","."));
        //获得两个分隔符中间方部分字符串
        System.out.println("获得两个分隔符中间方部分字符串："+StringUtils.substringBetween("jia.shn.com",".","."));
        //有指定字符串填充字符串两边
        System.out.println("有指定字符串填充字符串两边："+StringUtils.wrap("jiashn",'*'));
        //如果指定的字符串不存在，则用这个字符串填充字符串的两边
        System.out.println("如果指定的字符串不存在，则用这个字符串填充字符串的两边："+StringUtils.wrapIfMissing("jiashn","#"));

    }
}