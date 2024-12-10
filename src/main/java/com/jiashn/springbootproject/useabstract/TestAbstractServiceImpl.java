package com.jiashn.springbootproject.useabstract;

import java.util.Arrays;
import java.util.List;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/8/27 14:41
 **/
public abstract class TestAbstractServiceImpl implements TestAbstractService {

    @Override
    public List<String> getNames() {
        return Arrays.asList("12121","33333");
    }
}
