package com.jiashn.springbootproject.desensitize.plugin;

import com.jiashn.springbootproject.desensitize.aop.Sensitive;
import com.jiashn.springbootproject.desensitize.enums.DesensitizedStrategy;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/4/15 16:32
 **/
@Intercepts(@Signature(type= ResultSetHandler.class,
        method = "handleResultSets",args = {Statement.class}))
public class DesensitizedPlugin implements Interceptor {

    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
       List<Object> records =  (List<Object>) invocation.proceed();
        records.forEach(this::desensitized);
        return records;
    }

    private void desensitized(Object obj){
        Class<?> aClass = obj.getClass();
        //mybatis中方法，主要是获取对象中对用名称、数据。
        MetaObject metaObject = SystemMetaObject.forObject(obj);
        // aClass.getDeclaredFields()获取当前对象的字段
        Arrays.stream(aClass.getDeclaredFields())
                //过滤是否存在有注解Sensitive的数据
                .filter(field -> field.isAnnotationPresent(Sensitive.class))
                .forEach(field -> doDesensitize(metaObject,field));
    }

    private void doDesensitize(MetaObject metaObject, Field field){
        //获取字段名称
        String name = field.getName();
        //获取对应的值
        Object value = metaObject.getValue(name);
        if (String.class == metaObject.getGetterType(name) && Objects.nonNull(value)){
            //获取注解
            Sensitive sensitive = field.getAnnotation(Sensitive.class);
            //获取注解的值
            DesensitizedStrategy strategy = sensitive.strategy();

            /* *
             *  strategy.getDesensitize()返回接口Desensitized，该接口继承Function<String,String>。
             *   Function中 R apply(T t)即传入类型T，返回类型R
             *   Function是定义的一个操作的lambda表达式，定义计算规则，用于运算自己想要的结果，例如字段的替换
             */
            String apply = strategy.getDesensitize().apply(String.valueOf(value));
            metaObject.setValue(name,apply);
        }
    }
}