package com.jiashn.springbootproject.changeDB.dynamic.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/2/22 14:41
 **/
@Data
@TableName("t_data_source")
public class DataSourceInfo {
    /**
     * 数据源名称
     */
    private String dataSourceName;
    /**
     * 数据源用户名
     */
    private String userName;
    /**
     * 数据源密码
     */
    private String password;
    /**
     * 数据源驱动
     */
    private String driver;
    /**
     * 数据源地址
     */
    private String url;
    /**
     * 数据源状态 1：可用 0：禁用
     */
    private int status;
}
