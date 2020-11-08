package com.spldeolin.beginningmind.entity;

import com.spldeolin.beginningmind.ancestor.EntityAncestor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限
 * <p>permission
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2018/11/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionEntity extends EntityAncestor {

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    private Boolean deleteFlag;

    /**
     * 映射方法（HTTP方法，e.g.: POST）
     * <p>mapping_method
     * <p>长度：255
     */
    private String mappingMethod;

    /**
     * 映射路由（控制器路由+方法路由，e.g.: /user/create）
     * <p>mapping_path
     * <p>长度：255
     */
    private String mappingPath;

    /**
     * 用于展示的名称
     * <p>display
     * <p>长度：255
     */
    private String display;

    /**
     * 是否所有用户都拥有本权限
     * <p>is_granted_for_all
     */
    private Boolean isGrantedForAll;

    /**
     * 是否所有用户都不拥有本权限
     * <p>is_forbidden
     */
    private Boolean isForbidden;

}
