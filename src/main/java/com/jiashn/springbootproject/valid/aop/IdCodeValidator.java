package com.jiashn.springbootproject.valid.aop;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/**
 * @author: jiangjs
 * @description: 身份证校验
 * @date: 2022/6/21 14:25
 **/
public class IdCodeValidator implements ConstraintValidator<IdCode,Object> {

        /**
         * 18位二代身份证号码的正则表达式
         */
    public static final String REGEX_ID_NO_18 = "^"
                // 6位地区码
            + "\\d{6}"
                // 年YYYY
            + "(18|19|([23]\\d))\\d{2}"
                // 月MM
            + "((0[1-9])|(10|11|12))"
                // 日DD
            + "(([0-2][1-9])|10|20|30|31)"
                // 3位顺序码
            + "\\d{3}"
                // 校验码
            + "[0-9Xx]"
            + "$";

    /**
     * 15位一代身份证号码的正则表达式
     */
    public static final String REGEX_ID_NO_15 = "^"
            + "\\d{6}" // 6位地区码
            + "\\d{2}" // 年YYYY
            + "((0[1-9])|(10|11|12))" // 月MM
            + "(([0-2][1-9])|10|20|30|31)" // 日DD
            + "\\d{3}"// 3位顺序码
            + "$";
    @Override
    public void initialize(IdCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String idCode = String.valueOf(o);
        if (idCode.length() != 15 && idCode.length() != 18){
           return false;
       }
        return idCode.matches(REGEX_ID_NO_18) || idCode.matches(REGEX_ID_NO_15);
    }
}