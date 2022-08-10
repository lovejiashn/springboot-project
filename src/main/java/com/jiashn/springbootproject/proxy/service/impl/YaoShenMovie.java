package com.jiashn.springbootproject.proxy.service.impl;

import com.jiashn.springbootproject.proxy.service.Movie;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/9 16:21
 **/
@Slf4j
public class YaoShenMovie implements Movie {

    @Override
    public void play() {
        log.info("正在播放《我不是药神》电影......");
    }
}