package com.jiashn.springbootproject.lock.distributed.redis.util;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/4/14 10:03
 **/
@FunctionalInterface
public interface ExecuteBusiness {
    /**
     * 执行业务逻辑
     */
    void execute();
}
