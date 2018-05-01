package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.*;
import javax.validation.constraints.*;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.spldeolin.beginningmind.valid.annotation.TextOption;
import com.spldeolin.beginningmind.model.Permission;

/**
 * “权限”Input类
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Data
@NoArgsConstructor
public class PermissionInput implements Serializable {

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

    public Permission toModel() {
        Permission model = Permission.builder().build();
        BeanUtils.copyProperties(this, model);
        return model;
    }

}