package com.spldeolin.beginningmind.service.javabean.cond;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * QT1001S-E73C252D
 *
 * @author Deolin 2024-04-05
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class QueryOrdersCond implements Serializable, Cloneable {

    private static final long serialVersionUID = 383054632466202624L;

    /**
     *
     */
    Integer studentNumber;

    /**
     *
     */
    List<Integer> studentNumberEx;

    /**
     *
     */
    Date orderDate;

    /**
     *
     */
    Date orderDateEx;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
