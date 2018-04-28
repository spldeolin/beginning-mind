package com.spldeolin.beginningmind.util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 映射Excel的一列， 声明了这个注解的私有属性，将会作成Excel工作表一列属性生成出来
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    String value() default "";

    /**
     * 列首行标题
     */
    String columnName() default "";

    /**
     * 格式化器
     */
    Class<? extends Formatter> formatter() default Formatter.class;

    /**
     * 属性为null的缺省值（仅在生成Excel时使用到这个值）
     */
    String defaultValue() default "";

}