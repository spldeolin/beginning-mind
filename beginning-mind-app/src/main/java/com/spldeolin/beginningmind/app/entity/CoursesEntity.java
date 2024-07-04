package com.spldeolin.beginningmind.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>courses
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CoursesEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 7865254131753546752L;

    /**
     * <p>courseCode
     * <p>长度：15
     * <p>不能为null
     */
    String courseCode;

    /**
     * <p>courseName
     * <p>长度：70
     * <p>不能为null
     */
    String courseName;

    /**
     * <p>courseLine
     * <p>长度：50
     * <p>不能为null
     */
    String courseLine;

    /**
     * <p>courseScale
     * <p>长度：10
     * <p>不能为null
     */
    String courseScale;

    /**
     * <p>courseVendor
     * <p>长度：50
     * <p>不能为null
     */
    String courseVendor;

    /**
     * <p>courseDescription
     * <p>长度：65535
     * <p>不能为null
     */
    String courseDescription;

    /**
     * <p>buyPrice
     * <p>不能为null
     */
    BigDecimal buyPrice;

    /**
     * <p>MSRP
     * <p>不能为null
     */
    BigDecimal msrp;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
