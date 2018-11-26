package com.spldeolin.beginningmind.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.spldeolin.beginningmind.core.api.IdGetable;
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
public class Permission implements IdGetable, Serializable {

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
     * 权限名（perms[xxxx]）
     */
    @TableField("name")
    private String name;

    /**
     * 请求方法的全路由（控制器路由+方法路由）
     */
    @TableField("mapping")
    private String mapping;

    /**
     * 用于展示的名称
     */
    @TableField("display")
    private String display;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private Long menuId;

    /**
     * 是否是GET请求
     */
    @TableField("is_get_request")
    private Boolean isGetRequest;

    /**
     * 是否所有用户都应该拥有该权限
     */
    @TableField("must_have")
    private Boolean mustHave;

    private static final long serialVersionUID = 1L;

}