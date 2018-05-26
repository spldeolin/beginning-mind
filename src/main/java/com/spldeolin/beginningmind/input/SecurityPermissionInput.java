/*
 * Generated by Cadeau Support.
 *
 * https://github.com/spldeolin/cadeau-support
 */

package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import com.spldeolin.beginningmind.model.SecurityPermission;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * “权限”Input类
 *
 * @author Deolin 2018/5/26
 */
@Data
@Accessors(chain = true)
public class SecurityPermissionInput implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 审计字段 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 用于展示的名称
     */
    @Size(max = 255)
    private String displayName;

    /**
     * 请求方法的全路由（控制器路由+方法路由）
     */
    @Size(max = 255)
    private String mapping;

    /**
     * 权限标记（perms[xxxx]）
     */
    @Size(max = 255)
    private String mark;

    private static final long serialVersionUID = 1L;

    public SecurityPermission toModel() {
        return SecurityPermission.builder().id(id).updatedAt(updatedAt).displayName(displayName).mapping(
                mapping).mark(mark).build();
    }

}