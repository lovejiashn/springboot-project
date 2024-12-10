package com.jiashn.springbootproject.reflect;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/5/30 16:55
 **/
public class RunDogReflect {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> aClass1 = Class.forName("com.jiashn.springbootproject.reflect.domain.Dog");
        Method[] setMethod = aClass1.getMethods();
        for (Method method1 : setMethod) {
            if (Objects.equals(method1.getName(),"getOther")){
                String returnType = method1.getReturnType().getTypeName();
                Object invoke = method1.invoke(aClass1.newInstance(), "123", 123);
                System.out.println("returnType:"+returnType);
                System.out.println("invoke:"+invoke);
            }
            if (Objects.equals(method1.getName(),"getInputStream")){
                String returnType = method1.getReturnType().getTypeName();
                Object invoke = method1.invoke(aClass1.newInstance());
                System.out.println("returnType:"+returnType);
                System.out.println("value:"+ (InputStream)invoke);
            }
        }
    }
}
