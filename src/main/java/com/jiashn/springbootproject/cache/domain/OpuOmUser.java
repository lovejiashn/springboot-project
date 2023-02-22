package com.jiashn.springbootproject.cache.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/23 14:25
 **/
@Data
@TableName("opu_om_user")
public class OpuOmUser implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String userId;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 密码到期时间
     */
    private Date pwdExpiredTime;

    /**
     * 钉钉账号
     */
    private String dingtalkAccount;

    /**
     * 钉钉ID
     */
    private String dingtalkId;

    /**
     * 微信账号
     */
    private String wechatAccount;

    /**
     * 微信ID
     */
    private String wechatId;

    /**
     * 登录名
     */
    private String userName;

    /**
     * 用户姓名拼音
     */
    private String userNamePinYin;

    /**
     * 性别  0 男 ， 1 女
     */
    private String userSex;

    /**
     * 登录密码是否加密。0表示密码为明文，1表示密码已加密。
     */
    private String isPwdEncrypted;

    /**
     * 加密盐
     */
    private String encryptSalt;

    /**
     * 密码强度等级
     */
    private Double pwdStrengthGrade;

    /**
     * 是否启用。0表示禁用，1表示启用。
     */
    private String isActive;

    /**
     * 是否拒收短信:0表示不拒收,1表示拒收
     */
    private String isRejectSms;

    /**
     * 启用开始时间
     */
    private Date activeBeginTime;

    /**
     * 启用结束时间
     */
    private Date activeEndTime;

    /**
     * 逻辑删除标记。0表示正常记录，1表示已删除记录。
     */
    private String userDeleted;

    /**
     * 创建人姓名
     */
    private String creater;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人姓名
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 是否原生管理员，0表示非原生管理员，1表示由组织初始化自动创建的管理员
     */
    private String isOriginalAdmin;

    /**
     * 是否锁定  0 未锁定 1锁定
     */
    private String isLock;

    /**
     * 登陆失败次数
     */
    private Integer loginFailNum;

    private Date lockTime;

    /**
     * 所属顶级机构Id
     */
    private String rootOrgId;

    /**
     * 最后修改密码时间
     */
    private Date modifyTimePwd;

    /**
     * 密码有效期（月）
     */
    private Integer pwdExpireType;

    private static final long serialVersionUID = 1L;
}