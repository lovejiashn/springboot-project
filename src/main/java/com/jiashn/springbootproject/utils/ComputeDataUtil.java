package com.jiashn.springbootproject.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/7/6 14:40
 **/
public class ComputeDataUtil {
    /**
     * 计算两个数的比例，保留四位小数
     * @param molecule 分子
     * @param denominator 分母
     * @return 返回计算结果
     */
    public static Double computeRadio(Object molecule,Object denominator){
        BigDecimal bdMolecule = getDataBigDecimal(molecule);
        BigDecimal bDenominator = getDataBigDecimal(denominator);
        if (Objects.isNull(bdMolecule) || Objects.isNull(bDenominator)){
            throw new RuntimeException("传递的数据类型非数字类型,请确认后再计算");
        }
        return bdMolecule.divide(bDenominator, 4, RoundingMode.HALF_UP).doubleValue();
    }

    public static BigDecimal getDataBigDecimal(Object number){
        try{
            if (number instanceof Double){
                return BigDecimal.valueOf((Double) number);
            } else if (number instanceof Integer){
                return BigDecimal.valueOf((Integer) number);
            }else if (number instanceof Float){
                return BigDecimal.valueOf((Float) number);
            }else if (number instanceof Long){
                return BigDecimal.valueOf((Long) number);
            } else if (number instanceof String){
                return BigDecimal.valueOf(Long.parseLong(String.valueOf(number)));
            } else {
                return null;
            }
        } catch (Exception e){
            throw new RuntimeException("传递的数据类型非数字类型,请确认后再计算");
        }
    }

    public static void main(String[] args) {
        Double aDouble = computeRadio("2", 4);
        System.out.println("计算结果：" + aDouble);
    }
}
