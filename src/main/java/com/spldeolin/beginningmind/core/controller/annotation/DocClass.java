package com.spldeolin.beginningmind.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于声明控制器。
 * <pre>
 * 未声明该注解的控制器依然能生成文档，
 * 但是生成的文档将会不属于任何菜单，
 * 不显示“开发者”。
 * </pre>
 *
 * @author Deolin 2018/06/02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocClass {

    /**
     * 控制器名称，将作为ShowDoc的左侧菜单名
     */
    String name();

    /**
     * 作者名（非必填）
     */
    String developer() default "";

    /**
     * 作成日期（非必填）
     */
    String date() default "";

}