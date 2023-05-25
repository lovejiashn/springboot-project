package com.jiashn.springbootproject.redis.breakdown;

import com.jiashn.springbootproject.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiangjs
 * @description: redis缓存击穿解决方案
 * @date: 2023/5/25 15:29
 **/
@RestController
@RequestMapping("/breakdown")
public class SolveBreakDown {

    private static final Logger log = LoggerFactory.getLogger(SolveBreakDown.class);

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    private static final Map<String,String> DATA_MAP = new HashMap<>(1);
    static {
        DATA_MAP.put("key","模拟数据库查询数据");
    };

    @GetMapping("/solve.do")
    public ResultUtil<String> solveBreakDown(){
        String val = redisTemplate.opsForValue().get("key");
        if (StringUtils.isBlank(val)){
            log.info("模拟查询数据库");
            //模拟数据库查询
            val = DATA_MAP.get("key");
            redisTemplate.opsForValue().set("key",val,1L, TimeUnit.MINUTES);
        }
        return ResultUtil.success(val);
    }
}
