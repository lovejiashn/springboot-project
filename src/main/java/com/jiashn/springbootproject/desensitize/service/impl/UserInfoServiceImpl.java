package com.jiashn.springbootproject.desensitize.service.impl;

import com.aspose.slides.internal.k4.se;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiashn.springbootproject.desensitize.domain.UserInfo;
import com.jiashn.springbootproject.desensitize.mapper.UserInfoMapper;
import com.jiashn.springbootproject.desensitize.service.UserInfoService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/4/15 14:06
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public ResultUtil<List<UserInfo>> getUserInfoPage(int pageNo, int pageSize) {
        //Page<UserInfo> page = new Page<>(pageNo,pageSize);
        //IPage<UserInfo> iPage = userInfoMapper.selectPage(page, null);
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<UserInfo> userInfoList = userInfoMapper.selectList(wrapper);
        //page.setRecords(userInfoList).setSize(1).setTotal(3);
        return ResultUtil.success(userInfoList);
    }
}