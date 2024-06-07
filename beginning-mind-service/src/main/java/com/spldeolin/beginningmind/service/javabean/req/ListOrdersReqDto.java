package com.spldeolin.beginningmind.service.javabean.req;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
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
public class ListOrdersReqDto {

    /**
     * 啊
     */
    @NotNull
    Integer studentNumber;

    /**
     * 是
     */
    @NotNull
    List<@Max(150) Integer> studentNumbers;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date orderDateLeft;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm::", timezone = "Asia/Shanghai")
    List<Date> orderDateLefts;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    Date orderDateRight;

    int pageNum;

    int pageSize;

}
