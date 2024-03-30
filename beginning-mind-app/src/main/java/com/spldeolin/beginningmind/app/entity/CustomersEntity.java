package com.spldeolin.beginningmind.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>customers
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-02-20
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CustomersEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 2574954191253082112L;

    /**
     * <p>customerNumber
     * <p>不能为null
     */
    Integer customerNumber;

    /**
     * <p>customerName
     * <p>长度：50
     * <p>不能为null
     */
    String customerName;

    /**
     * <p>contactLastName
     * <p>长度：50
     * <p>不能为null
     */
    String contactLastName;

    /**
     * <p>contactFirstName
     * <p>长度：50
     * <p>不能为null
     */
    String contactFirstName;

    /**
     * <p>phone
     * <p>长度：50
     * <p>不能为null
     */
    String phone;

    /**
     * <p>addressLine1
     * <p>长度：50
     * <p>不能为null
     */
    String addressLine1;

    /**
     * <p>addressLine2
     * <p>长度：50
     */
    String addressLine2;

    /**
     * <p>city
     * <p>长度：50
     * <p>不能为null
     */
    String city;

    /**
     * <p>state
     * <p>长度：50
     */
    String state;

    /**
     * <p>postalCode
     * <p>长度：15
     */
    String postalCode;

    /**
     * <p>country
     * <p>长度：50
     * <p>不能为null
     */
    String country;

    /**
     * <p>salesRepEmployeeNumber
     */
    Integer salesRepEmployeeNumber;

    /**
     * <p>creditLimit
     */
    BigDecimal creditLimit;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
