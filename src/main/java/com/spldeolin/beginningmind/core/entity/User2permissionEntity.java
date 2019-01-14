package com.spldeolin.beginningmind.core.entity;

import java.io.Serializable;
import java.time.*;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 用户与权限的关联关系
 *
 * @author Deolin 2018/12/07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("user2permission")
public class User2permissionEntity implements Serializable {

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
    @TableField("version")
    private Integer version;

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