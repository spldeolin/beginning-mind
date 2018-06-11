package com.spldeolin.beginningmind.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于声明请求方法的返回数据类型
 * <p>
 * 本注解不会对项目产生任何影响，但是缺失的话，可能会使文档生成器无法正确地生成文档
 *
 * @author Deolin 2018/06/11
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReturnStruction {

    /**
     * 返回值类型
     */
    Class type();

    /**
     * 返回值是否为数组、List、Set
     */
    boolean isArray() default false;

    /**
     * 返回值是否为分页对象
     */
    boolean isPage() default false;

}
