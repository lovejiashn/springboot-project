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

    }
}