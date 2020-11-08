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

    /**
     * 最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致
     * <p>updated_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    private Date updatedAt;

}