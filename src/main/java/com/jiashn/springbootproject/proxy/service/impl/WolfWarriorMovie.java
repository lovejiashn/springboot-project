package com.jiashn.springbootproject.proxy.service.impl;

import com.jiashn.springbootproject.proxy.service.Movie;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/9 16:41
 **/
@Slf4j
public class WolfWarriorMovie implements Movie {
    @Override
    public void play() {
        log.info("正在播放《战狼》电影......");
    }
}