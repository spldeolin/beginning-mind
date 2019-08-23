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
     * 指从第几行开始是数据内容
     * Excel的行号是从1开始开始的，1是标题行，所以指定小于1的值没有意义
     */
    int firstDataRowNo() default 2;

}
