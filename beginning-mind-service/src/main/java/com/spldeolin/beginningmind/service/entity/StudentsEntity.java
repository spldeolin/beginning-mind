package com.spldeolin.beginningmind.service.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import com.spldeolin.beginningmind.service.enums.StudentsStateEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>students
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentsEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 7519643616395353088L;

    /**
     * <p>studentNumber
     * <p>不能为null
     */
    Integer studentNumber;

    /**
     * <p>LastName
     * <p>长度：50
     * <p>不能为null
     */
    String lastName;

    /**
     * <p>FirstName
     * <p>长度：50
     * <p>不能为null
     */
    String firstName;

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
    StudentsStateEnum state;

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
