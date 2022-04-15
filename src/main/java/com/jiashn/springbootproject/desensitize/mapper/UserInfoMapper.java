package com.jiashn.springbootproject.desensitize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiashn.springbootproject.desensitize.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
/**
 * @author: jiangjs
 * @description:
 * @date: 2022/4/15 14:07
 **/
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
