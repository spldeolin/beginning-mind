package com.spldeolin.beginningmind.biz.entity;

import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spldeolin.beginningmind.core.common.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Deolin 2019-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_demo")
public class BizDemoEntity extends CommonEntity {

    /**
     * 证件号码
     */
    @TableField("credential_number")
    private String credentialNumber;

    /**
     * 证件类型（0请选择，1身份证，2护照，3军人证，4其他）
     */
    @TableField("credential_type")
    private Byte credentialType;

    /**
     * 学历（0请选择，1初，2高，3专，4本，5硕，7博，8其他）
     */
    @TableField("education_level")
    private Byte educationLevel;

    /**
     * E-mail
     */
    @TableField("email")
    private String email;

    /**
     * 能否登录
     */
    @TableField("enable_sign")
    private Boolean enableSign;

    /**
     * 入职时间
     */
    @TableField("induction_date")
    private LocalDate inductionDate;

    /**
     * 是否允许修改物业开发月报的第1栏
     */
    @TableField("is_permitted_update_development_monthly_1")
    private Boolean isPermittedUpdateDevelopmentMonthly1;

    /**
     * 是否允许修改物业开发月报的第2栏
     */
    @TableField("is_permitted_update_development_monthly_2")
    private Boolean isPermittedUpdateDevelopmentMonthly2;

    /**
     * 是否允许修改物业开发周报的第1栏
     */
    @TableField("is_permitted_update_development_weekly_1")
    private Boolean isPermittedUpdateDevelopmentWeekly1;

    /**
     * 是否允许修改物业开发周报的第2栏
     */
    @TableField("is_permitted_update_development_weekly_2")
    private Boolean isPermittedUpdateDevelopmentWeekly2;

    /**
     * 是否允许修改半月报的第1栏
     */
    @TableField("is_permitted_update_half_monthly_1")
    private Boolean isPermittedUpdateHalfMonthly1;

    /**
     * 是否允许修改停车月报的第1栏
     */
    @TableField("is_permitted_update_parking_monthly_1")
    private Boolean isPermittedUpdateParkingMonthly1;

    /**
     * 是否允许修改停车月报的第2栏
     */
    @TableField("is_permitted_update_parking_monthly_2")
    private Boolean isPermittedUpdateParkingMonthly2;

    /**
     * 是否允许修改停车周报的第1栏
     */
    @TableField("is_permitted_update_parking_weekly_1")
    private Boolean isPermittedUpdateParkingWeekly1;

    /**
     * 是否允许修改停车周报的第2栏
     */
    @TableField("is_permitted_update_parking_weekly_2")
    private Boolean isPermittedUpdateParkingWeekly2;

    /**
     * 是否允许修改停车周报的第3栏
     */
    @TableField("is_permitted_update_parking_weekly_3")
    private Boolean isPermittedUpdateParkingWeekly3;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 名字
     */
    @TableField("name")
    private String name;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 毕业院校
     */
    @TableField("school")
    private String school;

    /**
     * 性别（0请选择，1男，2女）
     */
    @TableField("sex")
    private Byte sex;

    /**
     * 座机号
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 工号
     */
    @TableField("user_number")
    private String userNumber;

    @TableField("wechat_nickname")
    private String wechatNickname;

    @TableField("wechat_openid")
    private String wechatOpenid;

    /**
     * 工作状态（0请选择，1在职，2停职，3停职留薪）
     */
    @TableField("work_status")
    private Byte workStatus;

    /**
     * 职称
     */
    @TableField("work_title")
    private String workTitle;

    private static final long serialVersionUID = 1L;

}