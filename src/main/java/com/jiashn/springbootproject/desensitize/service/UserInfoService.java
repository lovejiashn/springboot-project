package com.jiashn.springbootproject.desensitize.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiashn.springbootproject.desensitize.domain.UserInfo;
import com.jiashn.springbootproject.utils.ResultUtil;

import java.util.List;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/4/15 14:00
 **/
public interface UserInfoService {

    /**
     * 分页获取用户列表信息
     * @param pageNo 当前页
     * @param pageSize 页大小
     * @return 返回值
     */
    ResultUtil<IPage<UserInfo>> getUserInfoPage(int pageNo, int pageSize);
}
