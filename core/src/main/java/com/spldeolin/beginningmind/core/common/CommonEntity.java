package com.spldeolin.beginningmind.core.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author Deolin 2019-01-14
 */
@Data
public abstract class CommonEntity implements Serializable {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 通用字段 插入时间
     */
    @TableField("inserted_at")
    @JsonIgnore
    private LocalDateTime insertedAt;

    /**
     * 通用字段 更新时间
     */
    @TableField("updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    /**
     * 通用字段 是否被删除
     */
    @TableLogic
    @TableField("is_deleted")
    @JsonIgnore
    private Boolean isDeleted;

    /**
     * 通用字段 数据版本
     */
    @Version
    private Integer version;

    private static final long serialVersionUID = 1L;

}
