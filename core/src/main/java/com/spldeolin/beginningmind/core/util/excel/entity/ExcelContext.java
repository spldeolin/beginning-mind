package com.spldeolin.beginningmind.core.util.excel.entity;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import com.spldeolin.beginningmind.core.util.excel.formatter.Converter;
import lombok.Data;

/**
 * Excels工具类的上下文
 *
 * @author Deolin 2018/07/07
 */
@Data
public class ExcelContext {

    private String fileExtension;

    private InputStream fileInputStream;

    /**
     * Sheet名
     */
    private String sheetName;

    /**
     * 从第几行开始
     */
    private Integer dataRowStartNo;

    private List<ColumnDefinition> columnDefinitions;

    @Data
    public static class ColumnDefinition {

        private String firstColumnName;

        private String columnLetter;

        private Integer columnNumber;

        private Field modelField;

        private Converter formatter;

        private String defaultValue;

    }

}

