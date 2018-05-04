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
 * 帐号（用于登录的信息）
 *
 * @author Deolin 2018/5/4
 * @generator Cadeau Support
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Table(name = "security_account")
public class SecurityAccount implements Serializable {

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
     * 登录者类型（1买家 2卖家）
     */
    @Column(name = "signer_type")
    private Integer signerType;

    /**
     * 登录者ID（逻辑外键）
     */
    @Column(name = "signer_id")
    private Long signerId;

    /**
     * “用户名”
     */
    private String username;

    private String mobile;

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

    private static final long serialVersionUID = 1L;
}