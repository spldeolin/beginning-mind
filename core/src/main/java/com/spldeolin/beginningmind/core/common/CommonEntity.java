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
 * Entity通用属性
 *
 * entity类必须一律继承这个类
 *
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
     * 插入时间
     */
    @TableField("inserted_at")
    @JsonIgnore
    private LocalDateTime insertedAt;

    /**
     * 插入数据时所处请求的insignia
     */
    @TableField("inserted_insignia")
    @JsonIgnore
    private String insertedInsignia;

    /**
     * 最近一次更新时间
     */
    @TableField("updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    /**
     * 最近一次更新数据时所处请求的insignia
     */
    @TableField("updated_insignia")
    @JsonIgnore
    private String updatedInsignia;

    /**
     * 是否被删除
     */
    @TableLogic
    @TableField("is_deleted")
    @JsonIgnore
    private Boolean isDeleted;

    /**
     * 数据版本
     */
    @Version
    private Integer version;

    private static final long serialVersionUID = 1L;

}
