package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spldeolin.beginningmind.model.SecurityPermission;
import lombok.Data;

/**
 * “权限”Input类
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Data
public class SecurityPermissionInput implements Serializable {

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
     * 权限名
     */
    @Size(max = 255)
    private String name;

    /**
     * 请求方法@RequiresPermissions注解属性的映射
     */
    @JsonProperty("requires_permissions_mapping")
    @Size(max = 255)
    private String requiresPermissionsMapping;

    private static final long serialVersionUID = 1L;

    public SecurityPermission toModel() {
        SecurityPermission model = SecurityPermission.builder().build();
        BeanUtils.copyProperties(this, model);
        return model;
    }

}