package com.spldeolin.beginningmind.service.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>orderdetails
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderdetailsEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 6868807325881096192L;

    /**
     * <p>orderNumber
     * <p>不能为null
     */
    Integer orderNumber;

    /**
     * <p>courseCode
     * <p>长度：15
     * <p>不能为null
     */
    String courseCode;

    /**
     * <p>quantityOrdered
     * <p>不能为null
     */
    Integer quantityOrdered;

    /**
     * <p>priceEach
     * <p>不能为null
     */
    BigDecimal priceEach;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
