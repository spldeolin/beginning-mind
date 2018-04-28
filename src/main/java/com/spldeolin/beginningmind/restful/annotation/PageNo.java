package com.spldeolin.beginningmind.restful.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 被声明的请求方法参数会被认为是“页码”。
 * <pre>
 * 这个注解等价于<code>@RequestParam(value = "page_no", required = false, defaultValue = "1")</code>
 *
 * 请求参数<code>page_no</code>将会绑定到被声明的目标对象，
 * 如果<code>page_no</code>未被指定，则目标对象缺省为1，
 * 如果<code>page_no</code>小于1，则目标对象设置为1。
 * </pre>
 */
@Documented
@Target({PARAMETER})
@Retention(RUNTIME)
public @interface PageNo {}