package com.jiashn.springbootproject.useUtil.usejava;

import java.util.Properties;

/**
 * @author: jiangjs
 * @description: Properties配置文件转bean
 * @date: 2024/12/11 10:36
 **/
public class PropertiesToBean {
    public static void main(String[] args) {
        Properties properties = loadProperties("jdbc.properties");
        String name = properties.getProperty("name");
        String value = properties.getProperty("value");
        System.out.println("获取姓名,name:"+name+";value:"+value);
    }

    private static Properties loadProperties(String filePath){
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(filePath));
        }catch (Exception e){
            e.printStackTrace();
        }
        return properties;
    }
}
