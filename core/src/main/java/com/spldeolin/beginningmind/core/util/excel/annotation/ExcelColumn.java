package com.spldeolin.beginningmind.core.util.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.spldeolin.beginningmind.core.util.excel.converter.CellConverter;

/**
 * 映射Excel的一列， 声明了这个注解的私有属性，将会作为Excel工作表一列属性生成出来
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    /**
     * 列标题
     */
    String columnTitle();

    /**
     * 单元格内容的转化策略
     * 若不指定，那么读这列时，只能解析简单的Java类型，写这列时，只能单纯调用toString()方法
     */
    Class<? extends CellConverter> cellConverter() default CellConverter.class;

    /**
     * 如果单元格没有内容，则缺省为这个值
     */
    String defaultCellContentWhenEmpty() default "";

}