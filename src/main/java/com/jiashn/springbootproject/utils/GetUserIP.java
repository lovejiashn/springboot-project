package com.jiashn.springbootproject.utils;

import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: jiangjs
 * @description: 获取当前真实IP 外部的
 * @date: 2022/5/23 16:42
 **/
public class GetUserIP {

    public static final Logger log = LoggerFactory.getLogger(GetUserIP.class);
    private static final String IP_URL = "https://ip.chinaz.com/";

    /**
     * 获取内网ip地址
     * @param request 请求
     * @return 返回Ip
     */
    public static String getUserLocationIpAddress(HttpServletRequest request){
        try {
            String realIp = request.getHeader("X-Real-IP");
            String forwardedIp = request.getHeader("X-Forwarded-For");
            String clientIp = request.getHeader("Proxy-Client-IP");
            String proxyIp = request.getHeader("WL-Proxy-Client-IP");
            String httpClientIp = request.getHeader("HTTP_CLIENT_IP");
            String xForwardedIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            String remoteAddress = InetAddress.getLocalHost().getHostAddress();
            if(StringUtils.isNotEmpty(forwardedIp) && !"unKnown".equalsIgnoreCase(forwardedIp)){
                //多次反向代理后会有多个Ip值，第一个Ip才是真实Ip
                int index = forwardedIp.indexOf(",");
                if(index != -1){
                    return forwardedIp.substring(0,index);
                }else{
                    return forwardedIp;
                }
            }
            return StringUtils.isNoneBlank(realIp) && !"unKnown".equalsIgnoreCase(realIp) ? realIp :
                    StringUtils.isNoneBlank(clientIp) && !"unKnown".equalsIgnoreCase(clientIp) ? clientIp :
                            StringUtils.isNoneBlank(proxyIp) && !"unKnown".equalsIgnoreCase(proxyIp) ? proxyIp :
                                    StringUtils.isNoneBlank(httpClientIp) && !"unKnown".equalsIgnoreCase(httpClientIp) ? httpClientIp :
                                            StringUtils.isNoneBlank(xForwardedIp) && !"unKnown".equalsIgnoreCase(xForwardedIp) ? xForwardedIp : remoteAddress;
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取用户Ip报错:{}",e.getMessage());
        }
        return "";
    }

    /**
     * 根据ip138获取外网ip地址
     * @return 返回IP
     */
    public static String getUserOuterIpAddress(){
        try {
            Document document = Jsoup.connect(IP_URL).get();
            Elements select = document.select("#leftinfo > div.IcpMain02.bor-t1s02 > div.WhoIpWrap.jspu > div.WhwtdWrap.bor-b1s.col-gray03 > span:nth-child(1)");
            return select.toString().substring(select.toString().indexOf(">") + 1, select.toString().lastIndexOf("<"));
        } catch (IOException e) {
            log.error("获取ip对应地址报错："+e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}