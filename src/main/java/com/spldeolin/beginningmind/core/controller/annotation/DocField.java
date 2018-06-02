package com.spldeolin.beginningmind.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于@RequestParam参数、@PathVariable参数、@PequestBody对象的属性。
 * <pre>
 * 未声明该注解的字段依然能生成在文档的“参数说明”和“返回值说明”中，
 * 但是生成的说明将无法显示“描述”
 * </pre>
 *
 * @author Deolin 2018/06/02
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DocField {

    /**
     * 字段描述
     */
    String desc();

}
