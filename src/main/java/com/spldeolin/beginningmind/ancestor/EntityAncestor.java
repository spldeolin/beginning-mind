package com.spldeolin.beginningmind.ancestor;

import java.util.Date;
import lombok.Data;

/**
 * 所有Entity的直接或间接父类
 *
 * @author Deolin 2020-11-06
 */
@Data
public class EntityAncestor {

    /**
     * 主键
     */
    private Long id;

    /**
     * 插入数据的时间
     * <p>inserted_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    private Date insertedAt;

    private Date updatedAt;

}