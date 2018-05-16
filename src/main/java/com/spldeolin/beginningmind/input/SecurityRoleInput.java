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
import com.spldeolin.beginningmind.model.SecurityRole;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * “角色”Input类
 *
 * @author Deolin 2018/5/16
 */
@Data
@Accessors(chain = true)
public class SecurityRoleInput implements Serializable {

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
     * 角色名
     */
    @Size(max = 255)
    private String name;

    private static final long serialVersionUID = 1L;

    public SecurityRole toModel() {
        return SecurityRole.builder().id(id).updatedAt(updatedAt).name(name).build();
    }

}