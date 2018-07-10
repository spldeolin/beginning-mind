/*
 * Generated by Cadeau Support.
 *
 * https://github.com/spldeolin/cadeau-support
 */

package com.spldeolin.beginningmind.core.input;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.*;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;
import com.spldeolin.beginningmind.core.valid.annotation.Option;
import com.spldeolin.beginningmind.core.model.SecurityUsers2permissions;

/**
 * “用户与权限的关联”Input类
 *
 * @author Deolin 2018/7/10
 */
@Data
@Accessors(chain = true)
public class SecurityUsers2permissionsInput implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 审计字段 更新时间
     */
    private LocalDateTime updatedAt;

    private Long userId;

    private Long permissionId;

    private static final long serialVersionUID = 1L;

    public SecurityUsers2permissions toModel() {
        return SecurityUsers2permissions.builder().id(id).updatedAt(updatedAt).userId(userId).permissionId(
                permissionId).build();
    }

}