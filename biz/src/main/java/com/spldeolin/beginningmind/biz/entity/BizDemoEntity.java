package com.spldeolin.beginningmind.biz.entity;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spldeolin.beginningmind.core.common.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务示例
 *
 * @author Deolin 2019-06-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_demo")
public class BizDemoEntity extends CommonEntity {

    /**
     * 工号
     */
    @TableField("user_number")
    @NotNull
    private String userNumber;

    /**
     * 名字
     */
    @TableField("name")
    @Size(max = 6)
    private String name;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * E-mail
     */
    @TableField("email")
    private String email;

    /**
     * 座机号
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 性别（0请选择，1男，2女）
     */
    @TableField("sex")
    private Byte sex;

    /**
     * 毕业院校
     */
    @TableField("school")
    private String school;

    /**
     * 学历（0请选择，1初，2高，3专，4本，5硕，7博，8其他）
     */
    @TableField("education_level")
    private Byte educationLevel;

    /**
     * 证件类型（0请选择，1身份证，2护照，3军人证，4其他）
     */
    @TableField("credential_type")
    private Byte credentialType;

    /**
     * 证件号码
     */
    @TableField("credential_number")
    private String credentialNumber;

    /**
     * 职称
     */
    @TableField("work_title")
    private String workTitle;

    /**
     * 入职时间
     */
    @TableField("induction_date")
    private LocalDate inductionDate;

    /**
     * 工作状态（0请选择，1在职，2停职，3停职留薪）
     */
    @TableField("work_status")
    private Byte workStatus;

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
     * 能否登录
     */
    @TableField("enable_sign")
    private Boolean enableSign;

    @TableField("wechat_openid")
    private String wechatOpenid;

    @TableField("wechat_nickname")
    private String wechatNickname;

    /**
     * 是否允许修改停车周报的第1栏
     */
    @TableField("is_permitted_update_parking_weekly_1")
    private Byte isPermittedUpdateParkingWeekly1;

    /**
     * 是否允许修改停车周报的第2栏
     */
    @TableField("is_permitted_update_parking_weekly_2")
    private Byte isPermittedUpdateParkingWeekly2;

    /**
     * 是否允许修改停车周报的第3栏
     */
    @TableField("is_permitted_update_parking_weekly_3")
    private Byte isPermittedUpdateParkingWeekly3;

    /**
     * 是否允许修改物业开发周报的第1栏
     */
    @TableField("is_permitted_update_development_weekly_1")
    private Byte isPermittedUpdateDevelopmentWeekly1;

    /**
     * 是否允许修改物业开发周报的第2栏
     */
    @TableField("is_permitted_update_development_weekly_2")
    private Byte isPermittedUpdateDevelopmentWeekly2;

    /**
     * 是否允许修改半月报的第1栏
     */
    @TableField("is_permitted_update_half_monthly_1")
    private Byte isPermittedUpdateHalfMonthly1;

    /**
     * 是否允许修改停车月报的第1栏
     */
    @TableField("is_permitted_update_parking_monthly_1")
    private Byte isPermittedUpdateParkingMonthly1;

    /**
     * 是否允许修改停车月报的第2栏
     */
    @TableField("is_permitted_update_parking_monthly_2")
    private Byte isPermittedUpdateParkingMonthly2;

    /**
     * 是否允许修改物业开发月报的第1栏
     */
    @TableField("is_permitted_update_development_monthly_1")
    private Byte isPermittedUpdateDevelopmentMonthly1;

    /**
     * 是否允许修改物业开发月报的第2栏
     */
    @TableField("is_permitted_update_development_monthly_2")
    private Byte isPermittedUpdateDevelopmentMonthly2;

    private static final long serialVersionUID = 1L;

}