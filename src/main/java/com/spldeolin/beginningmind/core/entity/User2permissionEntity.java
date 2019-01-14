package com.spldeolin.beginningmind.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spldeolin.beginningmind.core.api.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户与权限的关联关系
 *
 * @author Deolin 2018/12/07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user2permission")
public class User2permissionEntity extends CommonEntity {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 权限ID
     */
    @TableField("permission_id")
    private Long permissionId;

    private static final long serialVersionUID = 1L;

}