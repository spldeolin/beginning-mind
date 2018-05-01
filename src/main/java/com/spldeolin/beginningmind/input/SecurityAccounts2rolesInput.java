package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spldeolin.beginningmind.model.SecurityAccounts2roles;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * “帐号与权限的关联”Input类
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Data
@NoArgsConstructor
public class SecurityAccounts2rolesInput implements Serializable {

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

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("role_id")
    private Long roleId;

    private static final long serialVersionUID = 1L;

    public SecurityAccounts2roles toModel() {
        SecurityAccounts2roles model = SecurityAccounts2roles.builder().build();
        BeanUtils.copyProperties(this, model);
        return model;
    }

}