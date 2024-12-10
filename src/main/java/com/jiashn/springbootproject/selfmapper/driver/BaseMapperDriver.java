package com.jiashn.springbootproject.selfmapper.driver;

import com.google.common.base.CaseFormat;
import com.jiashn.springbootproject.selfmapper.annotation.*;
import com.jiashn.springbootproject.selfmapper.config.MybatisConfig;
import com.jiashn.springbootproject.selfmapper.config.MybatisMapperRegistry;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/8/13 9:50
 **/
public class BaseMapperDriver extends XMLLanguageDriver implements LanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        //当前mapper
        Class<?> mapperClazz = configuration instanceof MybatisConfig ?  MybatisMapperRegistry.getCurrentMapper() : null;
        if (Objects.isNull(mapperClazz)){
            throw new RuntimeException("解析SQL报错");
        }
        //处理SQL
        Class<?>[] generics = this.getMapperGenerics(mapperClazz);
        Class<?> modelClazz = generics[0];
        Class<?> idClazz = generics[1];
        //表名
        script = getTable(script,modelClazz);
        //主键Id
        script = getId(script,modelClazz);
        //插入
        script = setValues(script,modelClazz);
        //修改
        script = setSets(script,modelClazz);
        return super.createSqlSource(configuration, script, parameterType);
    }

    private String setSets(String script, Class<?> modelClazz) {
        final Pattern pattern = Pattern.compile("\\$\\{sets\\}");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()){
            StringBuilder builder = new StringBuilder();
            builder.append("<set>");
            //是否使用父类属性
            if (modelClazz.isAnnotationPresent(UseParent.class)){
                Class<?> superclass = modelClazz.getSuperclass();
                for (Field field : superclass.getDeclaredFields()) {
                    //非pulic和protected不处理
                    if (!(Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers()))){
                        continue;
                    }
                    //如果不显示，直接返回
                    if (field.isAnnotationPresent(Invisiable.class)){
                        continue;
                    }
                    String temp = "<if test = \"__field != null\">_column=#{_field},</if>";
                    //非驼峰命名规则
                    if (field.isAnnotationPresent(Column.class)){
                        builder.append(temp.replaceAll("_field", field.getName())
                                .replaceAll("_column", field.getAnnotation(Column.class).name()));
                        continue;
                    }
                    //驼峰命名规则
                    builder.append(temp.replaceAll("_field", field.getName())
                            .replaceAll("_column", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,field.getName())));
                }
            }
            //当前类属性
            for (Field field : modelClazz.getDeclaredFields()) {
                //不显示，则直接返回
                if (field.isAnnotationPresent(Invisiable.class) || field.isAnnotationPresent(Id.class)){
                    continue;
                }
                String temp = "<if test = \"__field != null\">_column=#{_field},</if>";
                //非驼峰命名规则
                if (field.isAnnotationPresent(Column.class)){
                    builder.append(temp.replaceAll("_field", field.getName())
                            .replaceAll("_column", field.getAnnotation(Column.class).name()));
                    continue;
                }
                //驼峰命名规则
                builder.append(temp.replaceAll("_field", field.getName())
                        .replaceAll("_column", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,field.getName())));
            }
            builder.deleteCharAt(builder.lastIndexOf(","));
            builder.append("</set>");
            script = matcher.replaceAll(builder.toString());
        }
        return script;
    }

    /**
     * 设置Value
     */
    private String setValues(String script, Class<?> modelClazz) {
        final Pattern pattern = Pattern.compile("\\$\\{values\\}");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()){
            StringBuilder builder = new StringBuilder();
            List<String> columns = new ArrayList<>();
            List<String> values = new ArrayList<>();
            //是否使用父类属性
            if (modelClazz.isAnnotationPresent(UseParent.class)){
                Class<?> superclass = modelClazz.getSuperclass();
                for (Field field : superclass.getDeclaredFields()) {
                    //非pulic和protected不处理
                    if (!(Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers()))){
                        continue;
                    }
                    //如果不显示，直接返回
                    if (field.isAnnotationPresent(Invisiable.class)){
                        continue;
                    }
                    values.add("#{"+field.getName()+"}");
                    columns.add(field.isAnnotationPresent(Column.class) ? field.getAnnotation(Column.class).name() :
                           CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,field.getName()));
                }
            }
            //本级属性
            for (Field field : modelClazz.getDeclaredFields()) {
                //不显示，则直接返回
                if (field.isAnnotationPresent(Invisiable.class)){
                    continue;
                }
                values.add("#{"+field.getName()+"}");
                columns.add(field.isAnnotationPresent(Column.class) ? field.getAnnotation(Column.class).name() :
                        CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,field.getName()));
            }
            builder.append("(").append(StringUtils.join(columns, ","))
                    .append(") values (")
                    .append(StringUtils.join(values, ","))
                    .append(")");
            script = matcher.replaceAll(builder.toString());
        }
        return script;
    }

    private Class<?>[] getMapperGenerics(Class<?> mapperClazz){
       Class[] classes = new Class[2];
       Type[] types = mapperClazz.getGenericInterfaces();
       for (Type type : types) {
           ParameterizedType parameterizedType = (ParameterizedType)type;
           Type[] arguments = parameterizedType.getActualTypeArguments();
           classes[0] = (Class<?>)arguments[0];
           classes[1] = (Class<?>)arguments[1];
       }
       return classes;
   }

   private String getTable(String script,Class<?> modelClazz){
       final Pattern pattern = Pattern.compile("\\$\\{table\\}");
       Matcher matcher = pattern.matcher(script);
       if (matcher.find()){
           script = modelClazz.isAnnotationPresent(Table.class) ?
                   script.replaceAll("\\$\\{table\\}",modelClazz.getAnnotation(Table.class).name()) :
                   script.replaceAll("\\$\\{table\\}", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,modelClazz.getSimpleName()));
       }
       return script;
   }

   private String getId(String script,Class<?> modelClazz){
        final Pattern pattern = Pattern.compile("\\$\\{id\\}");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()){
            boolean exit = false;
            for (Field field : modelClazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)){
                    script = script.replaceAll("\\$\\{id\\}",field.getAnnotation(Id.class).name());
                    exit = true;
                    break;
                }
            }
            if (!exit){
                script = script.replaceAll("\\$\\{id\\}","id");
            }
        }
        return script;
   }
}
