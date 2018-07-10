package com.spldeolin.beginningmind.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 权限
 *
 * @author Deolin 2018/5/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Table(name = "security_permission")
public class SecurityPermission implements Serializable {

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
    //@Version
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 审计字段 删除标记（-1代表未被删除，其他代表被删除）
     */
    @Column(name = "deletion_flag")
    @JsonIgnore
    private Long deletionFlag;

    /**
     * 权限名（perms[xxxx]）
     */
    private String name;

    /**
     * 请求方法的全路由（控制器路由+方法路由）
     */
    private String mapping;

    /**
     * 用于展示的名称
     */
    private String display;

    /**
     * 菜单ID
     */
    @Column(name = "security_menu_id")
    private Long securityMenuId;

    /**
     * 是否所有用户都应该拥有该权限
     */
    @Column(name = "must_have")
    private Boolean mustHave;

    private static final long serialVersionUID = 1L;
}