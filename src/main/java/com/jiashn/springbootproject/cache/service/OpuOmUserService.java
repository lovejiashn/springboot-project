package com.jiashn.springbootproject.cache.service;

import com.jiashn.springbootproject.cache.domain.OpuOmUser;
import com.jiashn.springbootproject.utils.ResultUtil;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/23 14:31
 **/
public interface OpuOmUserService {
    /**
     * 获取当前userId的用户信息
     * @param userId 用户Id
     * @return 查询结果
     */
    ResultUtil<OpuOmUser> getUserInfoByUserId(String userId);
    /**
     * 更新当前userId的用户信息
     * @param userId 用户Id
     * @return 查询结果
     */
    ResultUtil<OpuOmUser> updateUserInfoByUserId(String userId);

    /**
     * 删除用户信息
     * @param userId 用户Id
     * @return 查询结果
     */
    ResultUtil<OpuOmUser> deleteUserInfoByUserId(String userId);
}
