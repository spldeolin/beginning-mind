package com.spldeolin.beginningmind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户
 *
 * @author Deolin 2018/4/4
 * @generator Cadeau Support
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Table(name = "user")
public class User implements Serializable {
    /**
     * 通用字段 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 通用字段 插入时间
     */
    @Column(name = "inserted_at")
    @JsonIgnore
    private Date insertedAt;

    /**
     * 通用字段 更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 通用字段 删除时间
     */
    @Column(name = "deleted_at")
    @JsonIgnore
    private Date deletedAt;

    /**
     * 名称
     */
    private String name;

    /**
     * 随机盐
     */
    private String salt;

    /**
     * 性别（m男 f女 n中 a无）
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * “是否”
     */
    private Boolean flag;

    /**
     * 年月日
     */
    private Date ymd;

    /**
     * 时分秒
     */
    private Date hms;

    /**
     * 年月日时分秒
     */
    private Date ymdhms;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 编号
     */
    @Column(name = "serial_number")
    private Long serialNumber;

    /**
     * 百分率
     */
    private Double percent;

    /**
     * 富文本
     */
    @Column(name = "rich_text")
    private String richText;

    private static final long serialVersionUID = 1L;
}