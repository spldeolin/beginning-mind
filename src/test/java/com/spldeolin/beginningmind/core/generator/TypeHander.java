package com.spldeolin.beginningmind.core.generator;

import com.spldeolin.beginningmind.core.generator.dto.ColumnDTO;

/**
 * @author Deolin 2018/11/14
 */
public class TypeHander {

    public static String toJavaTypeName(ColumnDTO column) {
        String columnType = column.getType();
        if (columnType.contains("text")) {
            columnType = "text";
        }

        switch (columnType) {
            case "varchar":
            case "char":
            case "text":
                return "String";
            case "bigint":
                return "Long";
            case "date":
                return "LocalDate";
            case "time":
                return "LocalTime";
            case "datetime":
            case "timestamp":
                return "LocalDateTime";
            case "int":
                return "Integer";
            case "decimal":
                return "BigDecimal";
            case "double":
                return "Double";
            case "tinyint":
                if (column.getIsTinyint1Unsigned()) {
                    return "Boolean";
                } else {
                    return "Byte";
                }
        }
        throw new RuntimeException("出现了未考虑到的类型 {}" + column);
    }

}
