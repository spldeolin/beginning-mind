package com.spldeolin.beginningmind.service.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>courselines
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CourselinesEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 888113807109566464L;

    /**
     * <p>courseLine
     * <p>长度：50
     * <p>不能为null
     */
    String courseLine;

    /**
     * <p>textDescription
     * <p>长度：4000
     */
    String textDescription;

    /**
     * <p>htmlDescription
     * <p>长度：4294967295
     */
    String htmlDescription;

    /**
     * <p>imageUrl
     * <p>长度：255
     */
    String imageUrl;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
