package com.spldeolin.beginningmind.core.util.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 映射Excel的一列， 声明了这个注解的类，将会作成Excel工作表生成出来
 */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSheet {

    /**
     * 工作表名
     */
    String sheetName() default "Sheet1";

    /**
     * 标题行的行号
     * Excel中，这个在这个行号内出现的有内容单元格，才会被认为是标题栏，所在的列才会被认为是有效列
     */
    int titleRowStartNo() default 1;

}
