package com.spldeolin.beginningmind.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户
 *
 * @author Deolin 2018/5/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Table(name = "security_user")
public class SecurityUser implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 审计字段 插入时间
     */
    @Column(name = "inserted_at")
    @JsonIgnore
    private LocalDateTime insertedAt;

    /**
     * 审计字段 更新时间
     */
    @Version
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 审计字段 是否被删除
     */
    @Column(name = "is_deleted")
    @JsonIgnore
    private Boolean isDeleted;

    /**
     * “用户名”
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * E-Mail
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 能否登录
     */
    @Column(name = "enable_sign")
    private Boolean enableSign;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    @Column(name = "headerUrl")
    private String headerurl;

    /**
     * 性别
     */
    private String sex;

    /**
     * 联系地址
     */
    private String address;

    private static final long serialVersionUID = 1L;
}