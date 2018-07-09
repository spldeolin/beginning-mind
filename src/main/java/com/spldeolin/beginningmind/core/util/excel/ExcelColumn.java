package com.spldeolin.beginningmind.core.util.excel;

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

    /**
     * 列首行名称
     */
    String firstColumnName() default "";

    /**
     * 列字母（Excel显示的“列号”，从A开始）
     */
    //String columnLetter() default "A";

    /**
     * 格式化策略
     */
    Class<? extends Formatter> formatter() default Formatter.class;

    /**
     * 如果单元格内容为空白，则内容缺省为这个值
     */
    String defaultValue() default "";

}