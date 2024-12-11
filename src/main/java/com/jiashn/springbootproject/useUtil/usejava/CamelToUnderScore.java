package com.jiashn.springbootproject.useUtil.usejava;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 驼峰转下划线， 下划线转驼峰
 * @date: 2024/12/10 11:19
 **/
public class CamelToUnderScore {
    public static void main(String[] args) {
        //自定义方法
        String hump = humpToUnderline("userName1");
        String underline = underlineToHump("user_name");
        System.out.println(hump);
        System.out.println(underline);
        //CaseFormat转换
        //驼峰转下划线
        String s = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "userName");
        //下划线转驼峰
        String s1 = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "user_name");
        System.out.println(s);
        System.out.println(s1);

        //hutool转换
        System.out.println(StrUtil.toCamelCase("user_name"));
        System.out.println(StrUtil.toUnderlineCase("userName"));
    }

    private static String humpToUnderline(String str) {
        if (StringUtils.isNoneBlank(str)){
            String[] ays = str.split("");
            StringBuilder builder = new StringBuilder();
            for (String ay : ays) {
               /* if (Character.isUpperCase(ay.charAt(0)) && !Objects.equals(ay, "_")) {
                    builder.append("_");
                }*/
                if (!Objects.equals(ay, ay.toLowerCase())  && !Objects.equals(ay, "_")){
                    builder.append("_");
                }
                builder.append(ay.toLowerCase());
            }
            return builder.toString();
        }
        return "";
    }

    private static String underlineToHump(String str) {
        if (StringUtils.isNoneBlank(str)){
            String[] ays = str.split("_");
            StringBuilder builder = new StringBuilder();
            int length = 0;
            for (String s : ays) {
                String start = s.substring(0, 1);
                String end = s.substring(1);
                String pattern = ".*[a-zA-Z].*";
                if (start.matches(pattern) && length != 0){
                    builder.append(start.toUpperCase());
                }else {
                    builder.append(start);
                }
                length++;
                builder.append(end);
            }
            return builder.toString();
        }
        return "";
    }
}
