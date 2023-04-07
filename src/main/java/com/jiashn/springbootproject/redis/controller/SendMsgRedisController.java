package com.jiashn.springbootproject.redis.controller;

import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/4/7 11:30
 **/
@RestController
@RequestMapping( "/redis")
public class SendMsgRedisController {

    @Autowired
    private RedisTemplate<String,String> template;

    @GetMapping("/send/{channel}/{msg}")
    public ResultUtil<String> sendMessage(@PathVariable("channel") String channel,
                                          @PathVariable("msg") String msg){
        template.convertAndSend(channel,msg);
        return ResultUtil.success();
    }
}
