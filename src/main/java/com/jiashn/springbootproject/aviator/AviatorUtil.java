package com.jiashn.springbootproject.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 封装aviator进行表达式计算 Expression expected
 * @date: 2023/3/27 10:52
 **/
@Component
public class AviatorUtil<T> {

    /**
     * 计算表达式
     * @param paraMap 参数数据，用于有时候需要进行数据替换
     * @param expression 计算表达式
     * @param <T> 返回值类型
     * @return 返回计算结果
     */
    public static <T> T computeExp(Map<String,Object> paraMap,String expression){
        Expression compile = AviatorEvaluator.compile(expression);
        return (T) (Objects.nonNull(paraMap) && paraMap.size() > 0 ? compile.execute(paraMap) : compile.execute());
    }
}
