package com.jiashn.springbootproject.conditional;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/24 14:47
 **/
public class WindowLoadBeanServiceImpl implements DiffSystemLoadBeanService {
    @Override
    public void showLoadSystem() {
        System.out.println("这是windows系统");
    }
}