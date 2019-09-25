package com.spldeolin.beginningmind.util.excel.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import com.spldeolin.beginningmind.util.excel.converter.CellConverter;
import lombok.Data;

/**
 * 列定义，数据来自POJO field上的的ExcelColumn注解
 *
 * @author Deolin 2019-08-25
 */
@Data
public class ColumnDefinition implements Serializable {

    private String firstColumnName;

    private String columnLetter;

    private Integer columnNumber;

    private Field modelField;

    private CellConverter formatter;

    private String defaultValue;

    private static final long serialVersionUID = 1L;

}