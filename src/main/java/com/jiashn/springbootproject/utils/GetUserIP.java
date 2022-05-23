package com.jiashn.springbootproject.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 获取当前真实IP
 * @date: 2022/5/23 16:42
 **/
public class GetUserIP {

    public static final String LOCATION_IP = "127.0.0.1";
    public static final Logger log = LoggerFactory.getLogger(GetUserIP.class);

    public static String getUserIpAddress(HttpServletRequest request){
        try {
            String realIp = request.getHeader("X-Real-IP");
            String forwardedIp = request.getHeader("X-Forwarded-For");
            String clientIp = request.getHeader("Proxy-Client-IP");
            String proxyIp = request.getHeader("WL-Proxy-Client-IP");
            String httpClientIp = request.getHeader("HTTP_CLIENT_IP");
            String xForwardedIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            String remoteAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println("realIp:"+realIp);
            System.out.println("forwardedIp:"+forwardedIp);
            System.out.println("clientIp:"+clientIp);
            System.out.println("proxyIp:"+proxyIp);
            System.out.println("httpClientIp:"+httpClientIp);
            System.out.println("xForwardedIp:"+xForwardedIp);
            System.out.println("remoteAddress:"+remoteAddress);
            if(StringUtils.isNotEmpty(forwardedIp) && !"unKnown".equalsIgnoreCase(forwardedIp)){
                //多次反向代理后会有多个Ip值，第一个Ip才是真实Ip
                int index = forwardedIp.indexOf(",");
                if(index != -1){
                    return forwardedIp.substring(0,index);
                }else{
                    return forwardedIp;
                }
            }
            return StringUtils.isNotEmpty(realIp) && !"unKnown".equalsIgnoreCase(realIp) ? realIp :
                    StringUtils.isNotEmpty(clientIp) && !"unKnown".equalsIgnoreCase(clientIp) ? clientIp :
                            StringUtils.isNotEmpty(proxyIp) && !"unKnown".equalsIgnoreCase(proxyIp) ? proxyIp :
                                    StringUtils.isNotEmpty(httpClientIp) && !"unKnown".equalsIgnoreCase(httpClientIp) ? httpClientIp :
                                            StringUtils.isNotEmpty(xForwardedIp) && !"unKnown".equalsIgnoreCase(xForwardedIp) ? xForwardedIp : remoteAddress;
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取用户Ip报错:{}",e.getMessage());
        }
        return "";

    }
}