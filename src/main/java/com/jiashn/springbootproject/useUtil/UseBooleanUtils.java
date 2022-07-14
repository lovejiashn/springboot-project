package com.jiashn.springbootproject.useUtil;

import org.apache.commons.lang3.BooleanUtils;

/**
 * @author: jiangjs
 * @description: 使用BooleanUtils
 * @date: 2022/7/12 10:25
 **/
public class UseBooleanUtils {

    public static void main(String[] args) {
        System.out.println("是否为true: "+BooleanUtils.isTrue(true));

        System.out.println("转数字true: "+BooleanUtils.toInteger(true));
        System.out.println("转数字false: "+BooleanUtils.toInteger(Boolean.FALSE));

        System.out.println("多条件或判断: "+BooleanUtils.or(new Boolean[]{true,false,true}));
        System.out.println("多条件与判断: "+BooleanUtils.and(new Boolean[]{true,false,true}));
        System.out.println("逻辑非: "+BooleanUtils.negate(true));
        System.out.println("比较: "+BooleanUtils.compare(true,true));
        System.out.println("转换成开关On或Off: "+BooleanUtils.toStringOnOff(true));
    }
}