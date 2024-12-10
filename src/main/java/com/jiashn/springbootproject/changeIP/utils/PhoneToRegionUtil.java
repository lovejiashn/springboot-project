package com.jiashn.springbootproject.changeIP.utils;

import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;

import java.util.Locale;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 基于google的libphonenumber将手机号转成地区及供应商信息
 * @date: 2023/11/30 11:03
 **/
public class PhoneToRegionUtil {

    //手机号基本工具类
    private final static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    // 运营商
    private final static PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();

    //地理信息
    private final static PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();

    /**
     * 验证当前手机号是否有效
     * @param phone 手机号
     * @return 校验结果
     */
    public static boolean isValidNumber(String phone){
        return phoneNumberUtil.isValidNumber(getPhoneNumber(phone));
    }

    /**
     * 获取手机号运营商
     * @param phone 手机号
     * @return 运营商
     */
    public static String getPhoneCarrier(String phone){
        return isValidNumber(phone) ?  carrierMapper.getNameForNumber(getPhoneNumber(phone), Locale.CHINA) : "";
    }

    /**
     * 获取手机号归属地
     * @param phone 手机号
     * @return 归属地
     */
    public static String getRegionInfoByPhone(String phone){
        return isValidNumber(phone) ? geocoder.getDescriptionForNumber(getPhoneNumber(phone),Locale.CHINESE) : "";
    }

    /**
     * 生成PhoneNumber
     * @param phone 手机号
     * @return PhoneNumber
     */
    private static Phonenumber.PhoneNumber getPhoneNumber(String phone){
        Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
        phoneNumber.setCountryCode(86);
        phoneNumber.setNationalNumber(Long.parseLong(phone));
        return phoneNumber;
    }
}
