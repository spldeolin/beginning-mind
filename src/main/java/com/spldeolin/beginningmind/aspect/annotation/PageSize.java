package com.spldeolin.beginningmind.aspect.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 被声明的请求方法参数会被认为是“每页数量”。
 * <pre>
 * 这个注解等价于
 *    <code>@RequestParam(value = "page_size", required = false, defaultValue = "10")</code>
 *
 * 请求参数<code>page_size</code>将会绑定到被声明的目标对象，
 * 如果<code>page_size</code>未被指定，则目标对象缺省为<code>1</code>，
 * 如果值小于1，则为设置为1，
 * 如果值大于50，则设置为50，这个数字可以通过@PageSize(limit = 50)来重新指定。
 * </pre>
 */
@Documented
@Target({PARAMETER})
@Retention(RUNTIME)
public @interface PageSize {

    int defaultSize() default 10;

    int limit() default 50;

}