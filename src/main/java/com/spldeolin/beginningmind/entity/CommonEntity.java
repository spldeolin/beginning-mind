package com.spldeolin.beginningmind.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * Entity通用属性
 *
 * entity类必须一律继承这个类
 *
 * @author Deolin 2019-01-14
 */
@Data
public abstract class CommonEntity {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 插入时间
     */
    @TableField(value = "inserted_at", fill = FieldFill.INSERT)
    private LocalDateTime insertedAt;

    /**
     * 插入数据时所处请求的insignia
     */
    @TableField(value = "inserted_insignia", fill = FieldFill.INSERT)
    private String insertedInsignia;

    /**
     * 最近一次更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 最近一次更新数据时所处请求的insignia
     */
    @TableField(value = "updated_insignia", fill = FieldFill.INSERT_UPDATE)
    private String updatedInsignia;

    /**
     * 是否被删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Boolean isDeleted;

    /**
     * 数据版本
     */
    @Version
    private Integer version;

}
