package com.jiashn.springbootproject.desensitize.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiashn.springbootproject.desensitize.aop.Sensitive;
import com.jiashn.springbootproject.desensitize.enums.DesensitizedStrategy;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/4/15 11:33
 **/
@Data
@TableName( autoResultMap = true,value = "t_user_info")
public class UserInfo implements Serializable{

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    @Sensitive(strategy = DesensitizedStrategy.USER_NAME)
    private String nickName;

    /**
     * 手机号
     */
    @Sensitive(strategy = DesensitizedStrategy.PHONE)
    private String phone;

    /**
     * 身份证号
     */
    @Sensitive(strategy = DesensitizedStrategy.ID_CARD)
    private String idCard;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GTM+8")
    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}