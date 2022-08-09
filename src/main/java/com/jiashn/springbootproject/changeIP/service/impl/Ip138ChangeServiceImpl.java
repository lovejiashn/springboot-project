package com.jiashn.springbootproject.changeIP.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jiashn.springbootproject.changeIP.service.Ip138ChangeService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/8 17:26
 **/
@Service
public class Ip138ChangeServiceImpl implements Ip138ChangeService {
    public static final String URL = "http://www.ip138.com/iplookup.asp?ip=";
    private static final Logger log = LoggerFactory.getLogger(Ip138ChangeServiceImpl.class);
    @Override
    public ResultUtil<JSONObject> getIpToAddress(String ip) {
        try {
            Document document = Jsoup.connect(URL + ip + "&action=2").get();
            Elements es = document.select("script").eq(1);
            for (Element e : es) {
                String[] data = e.data().split("var");
                for (String variable : data) {
                    if (variable.contains("=") && variable.contains("ip_result")){
                        String[] va = StringUtils.split(variable, "=");
                        String value = va[1].trim().substring(0,va[1].trim().length() - 1);
                        JSONObject object = JSONObject.parseObject(value);
                        JSONObject resJson = new JSONObject();
                        resJson.put("address", object.getString("ASN归属地"));
                        return ResultUtil.success(resJson);
                    }
                }
            }
            return ResultUtil.error("未找到地址");
        } catch (IOException e) {
            log.error("获取ip对应地址报错："+e.getMessage());
            e.printStackTrace();
            return ResultUtil.error("查询失败");
        }
    }
}