package com.spldeolin.beginningmind.core.model;

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
 * 角色与权限的关联
 *
 * @author Deolin 2018/5/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Table(name = "security_roles2permissions")
public class SecurityRoles2permissions implements Serializable {

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

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_id")
    private Long permissionId;

    private static final long serialVersionUID = 1L;
}