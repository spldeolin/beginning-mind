package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spldeolin.beginningmind.model.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * “帐号（用于登录的信息）”Input类
 *
 * @author Deolin 2018/4/30
 * @generator Cadeau Support
 */
@Data
@NoArgsConstructor
public class AccountInput implements Serializable {

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
     * 审计字段 是否被删除
     */
    @JsonProperty("is_deleted")
    private Boolean isDeleted;

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

    /**
     * 密码
     */
    @Size(max = 16)
    private String password;

    /**
     * 能否登录
     */
    @JsonProperty("enable_sign")
    private Boolean enableSign;

    private static final long serialVersionUID = 1L;

    public Account toModel() {
        Account model = Account.builder().build();
        BeanUtils.copyProperties(this, model);
        return model;
    }

}