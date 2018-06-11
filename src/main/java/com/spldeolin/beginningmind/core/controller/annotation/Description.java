package com.spldeolin.beginningmind.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于声明以下元素的描述
 * <pre>
 * 控制器
 * 请求方法
 * 请求方法的参数
 * 各种DTO的Field
 * </pre>
 * 本注解不会对项目产生任何影响，但是缺失的话，可能会使文档生成器无法正确地生成文档
 *
 * @author Deolin 2018/06/11
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {

    /**
     * 描述
     */
    String value();

}
