package com.spldeolin.beginningmind.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.spldeolin.beginningmind.core.api.IdGetable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户
 *
 * @author Deolin 2018/11/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("user")
public class User implements IdGetable, Serializable {

    /**
     * ID
     */
    @TableField("id")
    private Long id;

    /**
     * 通用字段 插入时间
     */
    @TableField("inserted_at")
    private LocalDateTime insertedAt;

    /**
     * 通用字段 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 通用字段 数据版本
     */
    @Version
    private Integer version;

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