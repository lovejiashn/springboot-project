package com.jiashn.springbootproject.shorUrl.sina;

import com.jiashn.springbootproject.shorUrl.ShortUrlServer;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/23 14:59
 **/
@Component
public class SinaShortUrlGenerator implements ShortUrlServer {

    private static final Logger log = LoggerFactory.getLogger(SinaShortUrlGenerator.class);

    @Value("${sina_short_url_key}")
    private String shortKey;
    @Value("${sina_api_url}")
    private String sinaApiUrl;

    @Override
    public String createShortUrl(String longUrl, String dateTime) {
        String shortUrl = "";
        try {
            RestTemplate template = new RestTemplate();
            String encodeUrl = URLEncoder.encode(longUrl, "UTF-8");
            String apiUrl = sinaApiUrl + "?appkey={appkey}&long_url={encodeUrl}";
            Map<String, String> paraMap = new HashMap<>(2);
            paraMap.put("appkey",shortKey);
            paraMap.put("encodeUrl",encodeUrl);
            paraMap.put("type","json");
            Result result = template.getForObject(apiUrl, Result.class, paraMap);
            shortUrl = Objects.nonNull(result) && result.getRs_code() == 0 ? result.getShort_url() : "";
        } catch (UnsupportedEncodingException e) {
            log.error("获取新浪短连接报错：{}",e.getMessage());
            e.printStackTrace();
        }
        return shortUrl;
    }

    @Data
    public static class Result{
        /**
         * 编码
         */
        private Integer rs_code;
        /**
         * 信息
         */
        private String rs_msg;
        /**
         * 长连接
         */
        private String long_url;
        /**
         * 短连接
         */
        private String short_url;
    }
}