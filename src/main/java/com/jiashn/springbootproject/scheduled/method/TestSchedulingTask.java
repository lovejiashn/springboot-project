package com.jiashn.springbootproject.scheduled.method;

import com.jiashn.springbootproject.valid.domain.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/2/16 16:24
 **/
@Slf4j
@Component(value = "testSchedulingTask")
public class TestSchedulingTask {

    public void taskMethod(UserInfo obj){
        log.info(String.format("调用了当前定时任务:输出参数：参数1：%s,参数2：%s",obj.getUserName(),obj.getPassword()));
    }
}
