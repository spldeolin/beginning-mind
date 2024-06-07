package com.spldeolin.beginningmind.service.entity;

import java.io.Serializable;
import com.spldeolin.beginningmind.service.enums.OfficeStateEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>offices
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OfficesEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 6825832118700019712L;

    /**
     * <p>officeCode
     * <p>长度：10
     * <p>不能为null
     */
    String officeCode;

    /**
     * <p>city
     * <p>长度：50
     * <p>不能为null
     */
    String city;

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
     * <p>state
     * <p>长度：50
     */
    OfficeStateEnum state;

    /**
     * <p>country
     * <p>长度：50
     * <p>不能为null
     */
    String country;

    /**
     * <p>postalCode
     * <p>长度：15
     * <p>不能为null
     */
    String postalCode;

    /**
     * <p>territory
     * <p>长度：10
     * <p>不能为null
     */
    String territory;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
