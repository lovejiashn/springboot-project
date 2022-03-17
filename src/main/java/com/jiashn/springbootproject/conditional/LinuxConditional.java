package com.jiashn.springbootproject.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2022/1/24 14:51
 **/
public class LinuxConditional implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Objects.requireNonNull(context.getEnvironment().getProperty("os.name")).contains("Linux");
    }
}