package com.spldeolin.beginningmind.core.util.excel;

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
     * Sheet序号
     */
    int sheetIndex() default 0;

    /**
     * 从第几行开始
     */
    int rowOffSet() default 0;

}
