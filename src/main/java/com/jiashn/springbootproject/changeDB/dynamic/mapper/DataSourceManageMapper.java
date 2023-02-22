package com.jiashn.springbootproject.changeDB.dynamic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiashn.springbootproject.changeDB.dynamic.domain.DataSourceInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/2/22 14:43
 **/
@Mapper
public interface DataSourceManageMapper extends BaseMapper<DataSourceInfo> {
}
