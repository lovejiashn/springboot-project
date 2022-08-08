package com.jiashn.springbootproject.changeIP.service.impl;

import com.jiashn.springbootproject.changeIP.service.Ip2RegionChangeService;
import com.jiashn.springbootproject.changeIP.utils.Ip2RegionUtil;
import com.jiashn.springbootproject.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/7/28 14:31
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Ip2RegionChangeServiceImpl implements Ip2RegionChangeService {

    private final Ip2RegionUtil regionUtil;
    @Override
    public ResultUtil<String> getIpToAdress(String ip) {
        return ResultUtil.success(regionUtil.changeIpToAdress(ip));
    }
}