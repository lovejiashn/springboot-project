package com.jiashn.springbootproject.changeIP.utils;

import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 创建Ip2Region工具类
 * @date: 2022/7/28 14:31
 **/
@Component
public class Ip2RegionUtil {
    public static final Logger log = LoggerFactory.getLogger(Ip2RegionUtil.class);

    /**
     * 将整个库进行缓存到内存，基于这个库创建查询对象来实现基于文件的查询
     * 获取Searcher
     */
    private Searcher getSearcher() throws IOException {
        //获取地址下的库数据
        byte[] bytes = Searcher.loadContentFromFile("D:\\ipchange\\ip2region\\ip2region.xdb");
        return Searcher.newWithBuffer(bytes);
    }

    /**
     * 根据ip地址直接返回国家、省、城市信息
     * @param ip ip
     * @return 返回地址
     */
    public String changeIpToAddress(String ip){
        //获取searcher
        Searcher searcher = null;
        try {
            searcher = getSearcher();
            return searcher.search(ip);
        }catch (Exception e){
            log.error(String.format("ip转地址报错:%s",e.getMessage()));
            e.printStackTrace();
            return "";
        }finally {
            try {
                if (Objects.nonNull(searcher)){
                    searcher.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}