package com.spldeolin.beginningmind.core.util.demo1;

import java.math.BigDecimal;
import java.util.Date;
import com.spldeolin.beginningmind.core.util.excel.annotation.ExcelColumn;
import com.spldeolin.beginningmind.core.util.excel.annotation.ExcelSheet;
import lombok.Builder;
import lombok.Data;

/**
 * 这是一个DTO，同时它也映射到Excel
 *
 * @author Deolin 2019-08-22
 */
@ExcelSheet(sheetName = "示例数据", titleRowStartNo = 2)
@Data
@Builder
public class DemoDTO {

    @ExcelColumn(columnTitle = "ID")
    private Long id;

    @ExcelColumn(columnTitle = "名称")
    private String name;

    @ExcelColumn(columnTitle = "第一种时间（自定义转换器）")
    private Date time1;

    @ExcelColumn(columnTitle = "时间（缺省转换器）")
    private Date time2;

    @ExcelColumn(columnTitle = "旗", defaultValue = "true")
    private Boolean flag;

    @ExcelColumn(columnTitle = "价格", defaultValue = "214.3")
    private BigDecimal price;

    @ExcelColumn(columnTitle = "数量")
    private Integer amount;

}
