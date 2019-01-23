package com.spldeolin.beginningmind.biz.entity;

import java.time.*;
import com.baomidou.mybatisplus.annotation.*;
import com.spldeolin.beginningmind.core.api.CommonEntity;
import lombok.*;

/**
 * @author Deolin 2019-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("table_aaa")
public class TableAaaEntity extends CommonEntity {

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

    @TableField("wechat_openid")
    private String wechatOpenid;

    @TableField("wechat_nickname")
    private String wechatNickname;

    @TableField("is_permitted_update_parking_weekly_1")
    private Boolean isPermittedUpdateParkingWeekly1;

    @TableField("is_permitted_update_parking_weekly_2")
    private Boolean isPermittedUpdateParkingWeekly2;

    @TableField("is_permitted_update_parking_weekly_3")
    private Boolean isPermittedUpdateParkingWeekly3;

    @TableField("is_permitted_update_development_weekly_1")
    private Boolean isPermittedUpdateDevelopmentWeekly1;

    @TableField("is_permitted_update_development_weekly_2")
    private Boolean isPermittedUpdateDevelopmentWeekly2;

    @TableField("is_permitted_update_half_monthly_1")
    private Boolean isPermittedUpdateHalfMonthly1;

    @TableField("is_permitted_update_parking_monthly_1")
    private Boolean isPermittedUpdateParkingMonthly1;

    @TableField("is_permitted_update_parking_monthly_2")
    private Boolean isPermittedUpdateParkingMonthly2;

    @TableField("is_permitted_update_development_monthly_1")
    private Boolean isPermittedUpdateDevelopmentMonthly1;

    @TableField("is_permitted_update_development_monthly_2")
    private Boolean isPermittedUpdateDevelopmentMonthly2;

    private static final long serialVersionUID = 1L;

}