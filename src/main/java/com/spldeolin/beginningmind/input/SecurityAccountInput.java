/*
 * Generated by Cadeau Support.
 *
 * https://github.com/spldeolin/cadeau-support
 */
package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spldeolin.beginningmind.model.SecurityAccount;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * “帐号”Input类
 *
 * @author Deolin 2018/5/16
 */
@Data
@Accessors(chain = true)
public class SecurityAccountInput implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 审计字段 更新时间
     */
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 登录者类型（1买家 2卖家）
     */
    @JsonProperty("signer_type")
    private Integer signerType;

    /**
     * 登录者ID（逻辑外键）
     */
    @JsonProperty("signer_id")
    private Long signerId;

    /**
     * “用户名”
     */
    @Size(max = 16)
    private String username;

    @Size(max = 20)
    private String mobile;

    @Size(max = 255)
    private String email;

    /**
     * 密码
     */
    @Size(max = 128)
    private String password;

    @Size(max = 32)
    private String salt;

    /**
     * 能否登录
     */
    @JsonProperty("enable_sign")
    private Boolean enableSign;

    private static final long serialVersionUID = 1L;

    public SecurityAccount toModel() {
        return SecurityAccount.builder().id(id).updatedAt(updatedAt).signerType(signerType).signerId(signerId).username(
                username).mobile(mobile).email(email).password(password).salt(salt).enableSign(enableSign).build();
    }

}