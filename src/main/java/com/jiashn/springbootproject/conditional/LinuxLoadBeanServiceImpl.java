package com.jiashn.springbootproject.conditional;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/24 14:48
 **/
public class LinuxLoadBeanServiceImpl implements DiffSystemLoadBeanService {
    @Override
    public void showLoadSystem() {
        System.out.println("这是Linux系统");
    }
}