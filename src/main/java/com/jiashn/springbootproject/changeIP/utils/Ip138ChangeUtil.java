package com.jiashn.springbootproject.changeIP.utils;

import com.alibaba.fastjson.JSONObject;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/9/20 16:12
 **/
public class Ip138ChangeUtil {

    private static final String URL = "http://www.ip138.com/iplookup.asp?ip=";
    private static final Logger log = LoggerFactory.getLogger(Ip138ChangeUtil.class);
    public static JSONObject getIpToAddress(String ip) throws IOException {
        JSONObject resJson = new JSONObject();
        Document document = Jsoup.connect(URL + ip + "&action=2").get();
        Elements es = document.select("script").eq(1);
        for (Element e : es) {
            String[] data = e.data().split("var");
            for (String variable : data) {
                if (variable.contains("=") && variable.contains("ip_result")){
                    String[] va = StringUtils.split(variable, "=");
                    String value = va[1].trim().substring(0,va[1].trim().length() - 1);
                    JSONObject object = JSONObject.parseObject(value);
                    log.info(String.format("获取到的数据值:%s",value));
                    resJson.put("address", object.getString("ASN归属地"));
                }
            }
        }
        return resJson;
    }
}
