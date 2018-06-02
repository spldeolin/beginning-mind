package com.spldeolin.beginningmind.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于声明请求方法。
 * <pre>
 * 未声明该注解的控制器依然能生成文档，
 * 但是生成的文档将不显示
 * “描述”“返回值示例”、“返回值说明”、“开发者”。
 *
 * <strong>**对于有返回值的接口，本注解非常重要**</strong>
 * </pre>
 *
 * @author Deolin 2018/06/02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocMethod {

    /**
     * 接口描述
     */
    String desc();

    /**
     * 返回值类型
     * <p>
     * （如果返回值是个List或Page对象，则指定泛型）
     * <p>
     * （如果return null，如更新类请求，则指定Void.class）
     */
    Class returnType();

    /**
     * 返回类型是否是List
     */
    boolean isReturnList() default false;

    /**
     * 返回类型是否是Page
     */
    boolean isReturnPage() default false;

    /**
     * 作者名（非必填）（值将会覆盖DocClass.developer的值）
     */
    String developer() default "";

    /**
     * 作者名（非必填）（值将会覆盖DocClass.date的值）
     */
    String date() default "";

}
