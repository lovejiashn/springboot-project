package com.jiashn.springbootproject.valid.aop;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author: jiangjs
 * @description: 身份证校验规则
 * @date: 2022/6/21 11:28
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Constraint(validatedBy = IdCodeValidator.class)
public @interface IdCode {
    String message() default "请填写正确的身份证信息";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
