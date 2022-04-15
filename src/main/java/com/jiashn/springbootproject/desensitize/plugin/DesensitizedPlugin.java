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
        return null;
    }

    private void desensitized(Object obj){
        Class<?> aClass = obj.getClass();
        MetaObject metaObject = SystemMetaObject.forObject(obj);
        Arrays.stream(aClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Sensitive.class))
                .forEach(field -> doDesensitize(metaObject,field));
    }

    private void doDesensitize(MetaObject metaObject, Field field){
        String name = field.getName();
        Object value = metaObject.getValue(name);
        if (String.class == metaObject.getGetterType(name) && Objects.nonNull(value)){
            Sensitive sensitive = field.getAnnotation(Sensitive.class);
            DesensitizedStrategy strategy = sensitive.strategy();
            String apply = strategy.getDesensitizer().apply(String.valueOf(value));
            metaObject.setValue(name,apply);
        }
    }
}