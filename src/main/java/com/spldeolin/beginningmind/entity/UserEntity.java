package com.spldeolin.beginningmind.entity;

import com.spldeolin.beginningmind.ancestor.EntityAncestor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 * <p>user
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2018/11/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends EntityAncestor {

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    private Boolean deleteFlag;

    /**
     * 用户编号
     * <p>serial_number
     * <p>长度：255
     */
    private String serialNumber;

    /**
     * 名字
     * <p>name
     * <p>长度：255
     */
    private String name;

    /**
     * 手机号
     * <p>mobile
     * <p>长度：20
     */
    private String mobile;

    /**
     * E-mail
     * <p>email
     * <p>长度：255
     */
    private String email;

    /**
     * 密码
     * <p>password
     * <p>长度：128
     */
    private String password;

    /**
     * 盐
     * <p>salt
     * <p>长度：32
     */
    private String salt;

    /**
     * 能否登录
     * <p>enable_sign
     */
    private Boolean enableSign;
}
