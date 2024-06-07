package com.spldeolin.beginningmind.service.javabean.record;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * QT1001S-6EE40EE2
 *
 * @author Deolin 2024-04-05
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class QueryOrderdetailsRecord implements Serializable, Cloneable {

    private static final long serialVersionUID = 7865888011041026048L;

    /**
     *
     */
    Integer orderNumber;

    /**
     *
     */
    String courseCode;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
