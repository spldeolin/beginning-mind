package com.spldeolin.beginningmind.service.javabean.resp;

import java.util.Collection;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * <p>Allison 1875 Lot No: HT1001S-BAE3BC9B
 *
 * @author Deolin 2024-02-20
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ListOrdersRespDto {

    Integer orderNumber;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date orderDate;

    /**
     * 需求日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date requiredDate;

    /**
     * 发货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date shippedDate;

    /**
     * 订单状态
     */
    String status;

    /**
     * 描述
     */
    String comments;

    /**
     * 课程编号
     */
    Collection<String> courseCodes;

}
