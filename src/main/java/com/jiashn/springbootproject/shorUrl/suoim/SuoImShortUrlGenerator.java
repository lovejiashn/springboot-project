package com.jiashn.springbootproject.shorUrl.suoim;

import com.jiashn.springbootproject.shorUrl.ShortUrlServer;
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
 * @Date: 2021/11/23 14:54
 **/
@Component
public class SuoImShortUrlGenerator implements ShortUrlServer {

    private static final Logger log = LoggerFactory.getLogger(SuoImShortUrlGenerator.class);
    /**
     * 短连接服务key (使用缩我短连接)
     */
    @Value("${shortUrl_key}")
    private String shortUrlKey;
    /**
     * 短连接服务地址
     */
    @Value("${shortApi_url}")
    private String apiServerUrl;

    @Override
    public String createShortUrl(String longUrl, String dateTime) {
        String shortUrl = "";
        try {
            String apiUrl = apiServerUrl + "?url={url}&format=json&key={key}&expireDate={expireDate}&domain={domain}";
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> paraMap = new HashMap<>(4);
            paraMap.put("url", URLEncoder.encode(longUrl,"UTF-8"));
            paraMap.put("key", shortUrlKey);
            paraMap.put("expireDate", dateTime);
            paraMap.put("domain", "mtw.so");
            Result result = restTemplate.getForObject(apiUrl, Result.class, paraMap);
            shortUrl = Objects.nonNull(result) ? result.getUrl() : "";
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