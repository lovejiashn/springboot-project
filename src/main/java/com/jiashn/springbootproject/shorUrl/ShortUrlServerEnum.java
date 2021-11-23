package com.jiashn.springbootproject.shorUrl;

import com.jiashn.springbootproject.shorUrl.sina.SinaShortUrlGenerator;
import com.jiashn.springbootproject.shorUrl.suoim.SuoImShortUrlGenerator;
import com.jiashn.springbootproject.utils.GetBeanUtil;

import java.util.Objects;

/**
 * @author jiangjs
 * @Description: 短连接生成服务
 */

public enum ShortUrlServerEnum {

    /**
     * 缩我短连接服务
     */
    SUOIM("suoim", GetBeanUtil.getBean(SuoImShortUrlGenerator.class)),
    /**
     * 新浪短连接服务
     */
    SINA("sina",GetBeanUtil.getBean(SinaShortUrlGenerator.class));

    private final String serverName;
    private final ShortUrlServer shortUrlServer;
    ShortUrlServerEnum(String serverName,ShortUrlServer shortUrlServer){
        this.serverName = serverName;
        this.shortUrlServer = shortUrlServer;
    }

    /**
     * 获取对应短链接服务枚举信息
     * @param serverName 服务名称
     * @return 短链接服务枚举信息
     */
    public static ShortUrlServerEnum getShortUrlServer(String serverName){
        ShortUrlServerEnum[] values = ShortUrlServerEnum.values();
        for (ShortUrlServerEnum urlServerEnum : values) {
            if (Objects.equals(urlServerEnum.getServerName(),serverName)){
                return urlServerEnum;
            }
        }
        return null;
    }

    public String getServerName() {
        return serverName;
    }

    public ShortUrlServer getShortUrlServer() {
        return shortUrlServer;
    }
}
