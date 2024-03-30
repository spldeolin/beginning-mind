package com.spldeolin.beginningmind.app.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>productlines
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-02-20
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductlinesEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 9054362912404348928L;

    /**
     * <p>productLine
     * <p>长度：50
     * <p>不能为null
     */
    String productLine;

    /**
     * <p>textDescription
     * <p>长度：4000
     */
    String textDescription;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
