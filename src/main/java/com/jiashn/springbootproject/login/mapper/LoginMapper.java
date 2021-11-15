package com.jiashn.springbootproject.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiashn.springbootproject.login.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/12 14:55
 **/
@Mapper
public interface LoginMapper extends BaseMapper<SysUser> {
}
