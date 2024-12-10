package com.jiashn.springbootproject.useUtil;

import com.jiashn.springbootproject.desensitize.domain.UserInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/9/29 14:49
 **/
public class StringUtil {
    public static String locateStrToLowerCase(String resource,String locateWord){
        if (StringUtils.containsIgnoreCase(resource, locateWord)){
            resource = StringUtils.replaceIgnoreCase(resource, locateWord, locateWord.toLowerCase());
        }
        return resource;
    }

    public static void main(String[] args) {
        /*String url = "http://192.168.2.156:7090/ddaada/[Var]/asasasa/[VAR]";
        String s = locateStrToLowerCase(url, "[Var]");
        System.out.println(s);*/
        UserInfo userInfo = new UserInfo().setUserName("2333332");
        if (Objects.isNull(userInfo) || StringUtils.isNoneBlank(userInfo.getUserName())){
            System.out.println("没有值");
        }
    }
}
