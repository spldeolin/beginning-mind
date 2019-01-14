package com.spldeolin.beginningmind.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 权限
 *
 * @author Deolin 2018/11/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("permission")
public class PermissionEntity implements Serializable {

    /**
     * ID
     */
    @TableField("id")
    private Long id;

    /**
     * 通用字段 插入时间
     */
    @TableField("inserted_at")
    private LocalDateTime insertedAt;

    /**
     * 通用字段 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 通用字段 是否被删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Boolean isDeleted;

    /**
     * 通用字段 数据版本
     */
    @Version
    private Integer version;

    /**
     * 映射方法（HTTP方法，e.g.: POST）
     */
    @TableField("mapping_method")
    private String mappingMethod;

    /**
     * 映射路由（控制器路由+方法路由，e.g.: /user/create）
     */
    @TableField("mapping_path")
    private String mappingPath;

    /**
     * 用于展示的名称
     */
    @TableField("display")
    private String display;

    /**
     * 是否所有用户都拥有本权限
     */
    @TableField("is_granted_for_all")
    private Boolean isGrantedForAll;

    /**
     * 是否所有用户都不拥有本权限
     */
    @TableField("is_forbidden")
    private Boolean isForbidden;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PermissionEntity that = (PermissionEntity) o;
        return Objects.equal(mappingMethod, that.mappingMethod) &&
                Objects.equal(mappingPath, that.mappingPath);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mappingMethod, mappingPath);
    }

}