package com.spldeolin.beginningmind.app.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>employees
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EmployeesEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 2965946259264637952L;

    /**
     * <p>employeeNumber
     * <p>不能为null
     */
    Integer employeeNumber;

    /**
     * <p>lastName
     * <p>长度：50
     * <p>不能为null
     */
    String lastName;

    /**
     * <p>firstName
     * <p>长度：50
     * <p>不能为null
     */
    String firstName;

    /**
     * <p>extension
     * <p>长度：10
     * <p>不能为null
     */
    String extension;

    /**
     * <p>email
     * <p>长度：100
     * <p>不能为null
     */
    String email;

    /**
     * <p>officeCode
     * <p>长度：10
     * <p>不能为null
     */
    String officeCode;

    /**
     * <p>reportsTo
     */
    Integer reportsTo;

    /**
     * <p>jobTitle
     * <p>长度：50
     * <p>不能为null
     */
    String jobTitle;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
