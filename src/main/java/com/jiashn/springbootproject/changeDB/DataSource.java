package com.jiashn.springbootproject.changeDB;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
/**
 * @Author: jiangjs
 */
public @interface DataSource {
    DataSourceType value() default DataSourceType.MYSQL;
}
