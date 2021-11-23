package com.jiashn.springbootproject.shorUrl;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/23 14:51
 **/
public interface ShortUrlServer {
    /**
     * 创建短连接服务
     * @param longUrl 长连接
     * @param dateTime 保留时长
     * @return 返回短连接
     */
    String createShortUrl(String longUrl,String dateTime);
}
