package com.spldeolin.beginningmind.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 所有“是一个权限资源”的控制层请求方法，都需要声明这个注解，
 * 以此来对外声明，“我的权限名是xxx”。
 * <p>
 * 遗漏该注解的请求方法，将会以控制器路由+方法路由作为其缺省权限名。
 */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionDisplayName {

    String value();

}
