package com.spldeolin.beginningmind.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>products
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-02-20
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductsEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 7951364311900925952L;

    /**
     * <p>productCode
     * <p>长度：15
     * <p>不能为null
     */
    String productCode;

    /**
     * <p>productName
     * <p>长度：70
     * <p>不能为null
     */
    String productName;

    /**
     * <p>productLine
     * <p>长度：50
     * <p>不能为null
     */
    String productLine;

    /**
     * <p>productScale
     * <p>长度：10
     * <p>不能为null
     */
    String productScale;

    /**
     * <p>productVendor
     * <p>长度：50
     * <p>不能为null
     */
    String productVendor;

    /**
     * <p>productDescription
     * <p>长度：65535
     * <p>不能为null
     */
    String productDescription;

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
