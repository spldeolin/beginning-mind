package com.spldeolin.beginningmind.entity;

import com.spldeolin.beginningmind.ancestor.EntityAncestor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户与权限的关联关系
 * <p>user2permission
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2018/12/07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User2permissionEntity extends EntityAncestor {

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    private Boolean deleteFlag;

    /**
     * 用户ID
     * <p>user_id
     */
    private Long userId;

    /**
     * 权限ID
     * <p>permission_id
     */
    private Long permissionId;
}
