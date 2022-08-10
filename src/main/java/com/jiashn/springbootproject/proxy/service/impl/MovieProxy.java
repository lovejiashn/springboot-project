package com.jiashn.springbootproject.proxy.service.impl;

import com.jiashn.springbootproject.proxy.service.Movie;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/9 16:22
 **/
@Slf4j
public class MovieProxy implements Movie {

    private final Movie movie;

    public MovieProxy(Movie movie){
        this.movie = movie;
    }
    @Override
    public void play() {
        startMovie();
        movie.play();
        endMovie();
    }

    private void startMovie(){
        log.info("放映前：广告播放中......");
    }

    private void endMovie(){
        log.info("放映后：影片已放完，广告播放中......");
    }
}