package com.spldeolin.beginningmind.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.Version;

/**
 * 用户
 *
 * @author Deolin 2018/4/15
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
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * wocao
     */
    @Column(name = "inserted_at")
    @JsonIgnore
    private LocalDateTime insertedAt;

    /**
     * 审计字段 更新时间
     */
    @Version
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 审计字段 是否被删除
     */
    @Column(name = "is_deleted")
    private Boolean isDeleted;

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
    private LocalDate ymd;

    /**
     * 时分秒
     */
    private LocalTime hms;

    /**
     * 年月日时分秒
     */
    private LocalDateTime ymdhms;

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