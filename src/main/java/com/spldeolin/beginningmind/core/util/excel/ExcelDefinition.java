package com.spldeolin.beginningmind.core.util.excel;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import lombok.Data;

/**
 * @author Deolin 2018/07/07
 */
@Data
public class ExcelDefinition {

    private String fileName;

    private String fileExtension;

    private InputStream fileInputStream;

    /**
     * Sheet序号
     */
    private Integer sheetIndex;

    /**
     * 从第几行开始
     */
    private Integer rowOffSet;

    private List<ColumnDefinition> columnDefinitions;

    @Data
    public static class ColumnDefinition {

        private String columnLetter;

        private Integer columnNumber;

        private Field modelField;

        private Formatter formatter;

        private String defaultValue;

    }

}

