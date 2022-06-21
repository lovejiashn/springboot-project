package com.jiashn.springbootproject.common;

import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * @author: jiangjs
 * @description: 统一异常处理方法
 * @date: 2022/6/21 11:28
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResultUtil<String> resolveViolationException(Exception ex){
        StringJoiner messages = new StringJoiner(",");
        if (ex instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                messages.add(violation.getMessage());
            }
        } else {
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            for (ObjectError error : allErrors) {
                messages.add(error.getDefaultMessage());
            }
        }
        return ResultUtil.error(400,String.valueOf(messages));
    }
}