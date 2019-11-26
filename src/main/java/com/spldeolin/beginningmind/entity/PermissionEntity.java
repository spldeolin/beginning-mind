package com.spldeolin.beginningmind.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.base.Objects;
import com.spldeolin.beginningmind.common.CommonEntity;
import lombok.Data;

/**
 * 权限
 *
 * @author Deolin 2018/11/15
 */
@Data
@TableName("permission")
public class PermissionEntity extends CommonEntity {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PermissionEntity that = (PermissionEntity) o;
        return Objects.equal(mappingMethod, that.mappingMethod) && Objects.equal(mappingPath, that.mappingPath);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mappingMethod, mappingPath);
    }

    public PermissionEntity(String mappingMethod, String mappingPath) {
        this.mappingMethod = mappingMethod;
        this.mappingPath = mappingPath;
    }

}