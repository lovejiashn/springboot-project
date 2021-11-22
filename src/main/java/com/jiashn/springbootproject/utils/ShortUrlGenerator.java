package com.jiashn.springbootproject.utils;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/22 14:46
 **/
@Component
public class ShortUrlGenerator {
    private static final Logger log = LoggerFactory.getLogger(ShortUrlGenerator.class);

    /**
     * 短连接服务key (使用缩我短连接)
     */
    @Value("${shortUrl_key}")
    private String shortUrlKey;

    /**
     * 生成短连接
     * @param url 长链接
     * @return 返回短连接数组
     */
    public String shortUrl(String url){
        String shortUrl = "";
        try {
            String apiUrl = "http://api.suowo.cn/api.htm?url={url}&format=json&key={key}&expireDate={expireDate}&domain={domain}";
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> paraMap = new HashMap<>(4);
            paraMap.put("url", URLEncoder.encode(url,"UTF-8"));
            paraMap.put("key", shortUrlKey);
            paraMap.put("expireDate", "2040-12-31");
            paraMap.put("domain", "0");
            Result result = restTemplate.getForObject(apiUrl, ShortUrlGenerator.Result.class, paraMap);
            shortUrl = result.getUrl();
        }catch (Exception e){
            log.error("获取短连接报错：{}",e.getMessage());
            e.printStackTrace();
        }
        return shortUrl;
    }

    @Data
  public static class Result {
        //处理结果：‘0’代表成功，‘1’代表失败
        private String code;
        //生成的短网址，如果生成失败，则返回原链接
        private String url;
        //异常描述
        private String err;
    }
}