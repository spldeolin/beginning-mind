package com.spldeolin.beginningmind.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于声明控制器或是请求方法的作者和作成日期
 * <p>
 * 本注解不会对项目产生任何影响，但是缺失的话，可能会使文档生成器无法正确地生成文档
 *
 * @author Deolin 2018/06/11
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Author {

    String value();

    String date() default "";

}
