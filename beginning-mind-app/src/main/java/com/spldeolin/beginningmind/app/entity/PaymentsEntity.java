package com.spldeolin.beginningmind.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>payments
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-02-20
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PaymentsEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 7421933002375058432L;

    /**
     * <p>customerNumber
     * <p>不能为null
     */
    Integer customerNumber;

    /**
     * <p>checkNumber
     * <p>长度：50
     * <p>不能为null
     */
    String checkNumber;

    /**
     * <p>paymentDate
     * <p>不能为null
     */
    Date paymentDate;

    /**
     * <p>amount
     * <p>不能为null
     */
    BigDecimal amount;

    /**
     * 已支付
     * <p>isPaid
     */
    Boolean isPaid;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
