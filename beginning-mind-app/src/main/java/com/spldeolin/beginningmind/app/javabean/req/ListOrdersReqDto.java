package com.spldeolin.beginningmind.app.javabean.req;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.spldeolin.beginningmind.app.enums.OfficesStateEnum;
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
public class ListOrdersReqDto {

    /**
     * 啊
     */
    @NotNull Integer customerNumber;

    /**
     * 是
     */
    @NotNull List<@Max(1) Integer> customerNumbers;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date orderDateLeft;

    List<Date> orderDateLefts;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date orderDateRight;

    OfficesStateEnum state;

    List<OfficesStateEnum> states;

    int pageNum;

    int pageSize;

}
