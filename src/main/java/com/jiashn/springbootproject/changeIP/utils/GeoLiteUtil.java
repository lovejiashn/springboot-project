package com.jiashn.springbootproject.changeIP.utils;

import com.alibaba.fastjson.JSONObject;
import com.maxmind.geoip2.DatabaseReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/5/23 15:12
 **/
public class GeoLiteUtil {

    public static final Logger log = LoggerFactory.getLogger(GeoLiteUtil.class);

    /**
     * 获取ip所在国家
     * @param ip 需查询的IP
     * @return 返回查询结果
     * @throws UnknownHostException 异常
     */
    private static String getCountry(String ip) throws Exception {
        DatabaseReader reader = getDatabaseReader();
        return Objects.nonNull(reader) ? reader.city(InetAddress.getByName(ip)).getCountry().getNames().get("zh-CN") : "";
    }

    /**
     * 获取ip所在省份
     * @param ip 需查询的IP
     * @return 返回查询结果
     * @throws UnknownHostException 异常
     */
    private static String getProvince(String ip) throws Exception {
        DatabaseReader reader = getDatabaseReader();
        return Objects.nonNull(reader) ? reader.city(InetAddress.getByName(ip)).getMostSpecificSubdivision().getNames().get("zh-CN") : "";

    }

    /**
     * 获取ip所在市
     * @param ip 需查询的IP
     * @return 返回查询结果
     * @throws UnknownHostException 异常
     */
    private static String getCity(String ip) throws Exception {
        DatabaseReader reader = getDatabaseReader();
        return Objects.nonNull(reader) ? reader.city(InetAddress.getByName(ip)).getCity().getNames().get("zh-CN") : "";
    }

    /**
     * 获取ip所在经纬度
     * @param ip 需查询的IP
     * @return 返回查询结果
     * @throws UnknownHostException 异常
     */
    private static JSONObject getLongitudeAndLatitude (String ip) throws Exception {
        DatabaseReader reader = getDatabaseReader();
        JSONObject resJson = new JSONObject();
        resJson.put("latitude",0d);
        resJson.put("longitude",0d);
        if (Objects.nonNull(reader)){
            //获取纬度信息
            Double latitude = reader.city(InetAddress.getByName(ip)).getLocation().getLatitude();
            //获取经度信息
            Double longitude = reader.city(InetAddress.getByName(ip)).getLocation().getLongitude();
            resJson.put("latitude",latitude);
            resJson.put("longitude",longitude);
        }
        return resJson;
    }

    private static DatabaseReader getDatabaseReader(){
        InputStream stream = null;
        try {
            stream = GeoLiteUtil.class.getClassLoader().getResourceAsStream("static/geo/GeoLite2-City.mmdb");
            return new DatabaseReader.Builder(stream).build();
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取geolite2数据库报错：{}",e.getMessage());
        }finally {
            try {
                if (Objects.nonNull(stream)){
                    stream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据Ip获取归属信息
     * @param ip 用户Ip
     * @return 返回查询结果
     * @throws Exception 异常
     */
    public static JSONObject getIpToAddress(String ip) throws Exception {
        StringJoiner address = new StringJoiner(",");
        address.add(getCountry(ip));
        address.add(getProvince(ip));
        address.add(getCity(ip));
        JSONObject resJson = GeoLiteUtil.getLongitudeAndLatitude(ip);
        resJson.put("address",String.valueOf(address));
        return resJson;
    }
}