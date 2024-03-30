package com.spldeolin.beginningmind.app.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>orders
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-02-20
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrdersEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 2520047326494133248L;

    /**
     * <p>orderNumber
     * <p>不能为null
     */
    Integer orderNumber;

    /**
     * <p>orderDate
     * <p>不能为null
     */
    Date orderDate;

    /**
     * <p>requiredDate
     * <p>不能为null
     */
    Date requiredDate;

    /**
     * <p>shippedDate
     */
    Date shippedDate;

    /**
     * <p>status
     * <p>长度：15
     * <p>不能为null
     */
    String status;

    /**
     * <p>comments
     * <p>长度：65535
     */
    String comments;

    /**
     * <p>customerNumber
     * <p>不能为null
     */
    Integer customerNumber;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
