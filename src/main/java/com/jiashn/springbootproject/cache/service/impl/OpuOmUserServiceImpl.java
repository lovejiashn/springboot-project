package com.jiashn.springbootproject.cache.service.impl;

import com.jiashn.springbootproject.cache.domain.OpuOmUser;
import com.jiashn.springbootproject.cache.mapper.OpuOmUserMapper;
import com.jiashn.springbootproject.cache.service.OpuOmUserService;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/23 14:32
 **/
@Service
public class OpuOmUserServiceImpl implements OpuOmUserService {
    @Resource
    private OpuOmUserMapper opuOmUserMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Cacheable(value = "user_cache",key = "#userId",unless ="#result == null")
    @Override
    public ResultUtil<OpuOmUser> getUserInfoByUserId(String userId) {
        OpuOmUser opuOmUser = opuOmUserMapper.selectById(userId);
        return ResultUtil.success(opuOmUser);
    }

    @CachePut(value = "user_cache",key = "#userId",unless ="#result == null")
    @Override
    public ResultUtil<OpuOmUser> updateUserInfoByUserId(String userId) {
        OpuOmUser opuOmUser = opuOmUserMapper.selectById(userId);
        opuOmUser.setUserName("管理员");
        opuOmUserMapper.updateById(opuOmUser);
        return ResultUtil.success(opuOmUser);
    }

    @CacheEvict(value = "user_cache",key = "#userId")
    @Override
    public ResultUtil<OpuOmUser> deleteUserInfoByUserId(String userId) {
        opuOmUserMapper.deleteById(userId);
        return ResultUtil.success("删除成功");
    }

    @Override
    public ResultUtil<?> insertUserInfoBatch() {
        //mybatis批量插入
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        OpuOmUserMapper mapper = sqlSession.getMapper(OpuOmUserMapper.class);
        List<OpuOmUser> users = new ArrayList<>();
        for (OpuOmUser user : users) {
            mapper.insert(user);
        }
        sqlSession.flushStatements();
        return ResultUtil.success("插入成功");
    }
}