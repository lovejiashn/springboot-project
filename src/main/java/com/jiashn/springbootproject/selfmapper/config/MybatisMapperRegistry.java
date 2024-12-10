package com.jiashn.springbootproject.selfmapper.config;

import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/8/13 10:08
 **/
public class MybatisMapperRegistry extends MapperRegistry {
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();
    private final Configuration config;

    private static Class<?> currentMapper;
    public MybatisMapperRegistry(Configuration config) {
        super(config);
        this.config = config;
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<T> factory = (MapperProxyFactory)this.knownMappers.get(type);
        if (Objects.isNull(factory)){
            throw new RuntimeException("没有找到对应的mapper");
        }
        return factory.newInstance(sqlSession);
    }

    @Override
    public <T> boolean hasMapper(Class<T> type) {
        return this.knownMappers.containsKey(type);
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()){
            if (this.hasMapper(type)){
                throw new RuntimeException("Type:"+type + "已经存在");
            }
            boolean loadCompleted = false;
            try {
                this.knownMappers.put(type,new MapperProxyFactory<>(type));
                MapperAnnotationBuilder builder = new MapperAnnotationBuilder(this.config, type);
                currentMapper = type;
                builder.parse();
                loadCompleted = true;
            }finally {
                if (!loadCompleted){
                    this.knownMappers.remove(type);
                }
            }

        }
    }

    @Override
    public Collection<Class<?>> getMappers() {
        return Collections.unmodifiableCollection(this.knownMappers.keySet());
    }

    @Override
    public void addMappers(String packageName, Class<?> superType) {
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
        resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
        Set<Class<? extends Class<?>>> mapperSet = resolverUtil.getClasses();
        for (Class<?> mapperClass : mapperSet) {
            this.addMapper(mapperClass);
        }
    }

    @Override
    public void addMappers(String packageName) {
        this.addMappers(packageName, Object.class);
    }

    public static Class<?> getCurrentMapper() {
        return currentMapper;
    }
}
