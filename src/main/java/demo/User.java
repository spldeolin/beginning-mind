package demo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ExcelSheet(sheetName = "用户")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

//    @ExcelColumn(columnName = "ID")
    private Long id;

    private Date insertedAt;

    private Date updatedAt;

    private Date deletedAt;

    @ExcelColumn(columnName = "名称", defaultValue = "无名")
    private String name;

    @ExcelColumn(columnName = "随机盐")
    private String salt;

    @ExcelColumn(columnName = "性别", defaultValue = "不详")
    private String sex;

//    @ExcelColumn(columnName = "年龄", defaultValue = "0")
    private Integer age;

//    @ExcelColumn(columnName = "“是否”")
    private Boolean flag;

    @ExcelColumn(columnName = "年月日", formatter = DateFormatter.class)
    private Date ymd;

    @ExcelColumn(columnName = "时分秒", formatter = TimeFormatter.class)
    private Date hms;

    @ExcelColumn(columnName = "年月日时分秒", formatter = DateTimeFormatter.class)
    private Date ymdhms;

//    @ExcelColumn(columnName = "金额")
    private BigDecimal money;

//    @ExcelColumn(columnName = "富文本")
    private String richText;

//    @ExcelColumn(columnName = "编号")
    private Long serialNumber;

//    @ExcelColumn(columnName = "百分率")
    private Double percent;

}
