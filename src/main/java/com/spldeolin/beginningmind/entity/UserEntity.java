package com.spldeolin.beginningmind.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spldeolin.beginningmind.common.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 *
 * @author Deolin 2018/11/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class UserEntity extends CommonEntity {

    /**
     * 用户编号
     */
    @TableField("serial_number")
    private String serialNumber;

    /**
     * 名字
     */
    @TableField("name")
    private String name;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * E-mail
     */
    @TableField("email")
    private String email;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 能否登录
     */
    @TableField("enable_sign")
    private Boolean enableSign;

    private static final long serialVersionUID = 1L;

}