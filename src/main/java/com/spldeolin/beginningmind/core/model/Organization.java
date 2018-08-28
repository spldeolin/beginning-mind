package com.spldeolin.beginningmind.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spldeolin.beginningmind.core.api.IdGetable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 组织架构
 *
 * @author Deolin 2018/8/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Table(name = "organization")
public class Organization implements IdGetable, Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 审计字段 插入时间
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
     * 审计字段 删除标记（-1代表未被删除，其他代表被删除）
     */
    @Column(name = "deletion_flag")
    @JsonIgnore
    private Long deletionFlag;

    /**
     * 父组织架构ID（0代表这是个顶层组织）
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    private static final long serialVersionUID = 1L;

    /**
     * 属性
     */
    public enum Property {
        id("id"),
        insertedAt("insertedAt"),
        updatedAt("updatedAt"),
        deletionFlag("deletionFlag"),
        parentId("parentId"),
        name("name");

        String fieldName;

        Property(String fieldName) {
            this.fieldName = fieldName;
        }
    }
}