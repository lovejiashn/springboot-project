package com.jiashn.springbootproject.spi.service;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/21 17:22
 **/
public interface UseSelService {

    Integer getTotalProjectNum(String startDate, String endDate, String regionCode);
}
