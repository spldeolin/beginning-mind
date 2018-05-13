package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spldeolin.beginningmind.model.SecurityRoles2permissions;
import lombok.Data;

/**
 * “角色与权限的关联”Input类
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Data
public class SecurityRoles2permissionsInput implements Serializable {

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

    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty("permission_id")
    private Long permissionId;

    private static final long serialVersionUID = 1L;

    public SecurityRoles2permissions toModel() {
        SecurityRoles2permissions model = SecurityRoles2permissions.builder().build();
        BeanUtils.copyProperties(this, model);
        return model;
    }

}