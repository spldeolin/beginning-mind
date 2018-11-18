package com.spldeolin.beginningmind.core.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被本注解修饰的Model类，其对应的CommonService中的删除类方法，将会不启用逻辑删除，直接物理删除。
 *
 * @author Deolin 2018/11/18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableLogicallyDelete {

}
