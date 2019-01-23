package com.spldeolin.beginningmind.biz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spldeolin.beginningmind.core.api.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Deolin 2019-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("table_bbb")
public class TableBbbEntity extends CommonEntity {

    /**
     * 工号
     */
    @TableField("user_number")
    private String userNumber;

    /**
     * 名字
     */
    @TableField("name")
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

    private static final long serialVersionUID = 1L;

}