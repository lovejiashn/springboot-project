package com.jiashn.springbootproject.desensitize.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String nickName;

    /**
     * 手机号
     */

    private String phone;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}