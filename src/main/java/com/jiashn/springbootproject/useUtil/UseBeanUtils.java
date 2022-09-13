package com.jiashn.springbootproject.useUtil;

import com.jiashn.springbootproject.useUtil.domain.Jiashn;
import com.jiashn.springbootproject.useUtil.domain.Queena;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author: jiangjs
 * @description: 使用org.springframework.beans包下的BeanUtils
 * @date: 2022/7/15 14:58
 **/
public class UseBeanUtils {

    public static void main(String[] args) {
        Jiashn jiashn = new Jiashn("jiashn","男",20);
        Queena queena = new Queena();
        //参数值复制
        BeanUtils.copyProperties(jiashn,queena);
        System.out.println("复制参数值："+queena);
        //反射实例化实体对象
        Jiashn jiashn1 = BeanUtils.instantiateClass(Jiashn.class);
        jiashn1.setName("张三");
        jiashn1.setGender("女");
        System.out.println("实例化："+ jiashn1);

        //获取指定类的指定方法
        Method declaredMethod = BeanUtils.findDeclaredMethod(Jiashn.class, "getName");
        assert declaredMethod != null;
        System.out.println("获取指定类的指定方法："+declaredMethod.getName());

        //获取指定方法的参数
        PropertyDescriptor propertyDescriptor = BeanUtils.findPropertyForMethod(declaredMethod);
        System.out.println("获取指定方法的参数:"+propertyDescriptor.getName());
    }
}