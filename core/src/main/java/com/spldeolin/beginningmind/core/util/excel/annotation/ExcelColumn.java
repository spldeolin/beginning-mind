package com.spldeolin.beginningmind.core.util.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.spldeolin.beginningmind.core.util.excel.formatter.Formatter;

/**
 * 映射Excel的一列， 声明了这个注解的私有属性，将会作为Excel工作表一列属性生成出来
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    /**
     * 列标题
     */
    String columnTitle() default "";

    /**
     * 格式化策略
     * 若不指定，读Excel时只能解析简单的Java类型，写Excel时只会单纯调用toString()方法
     */
    Class<? extends Formatter> formatter() default Formatter.class;

    /**
     * 如果单元格没有内容，则缺省为这个值
     */
    String defaultValue() default "";

}