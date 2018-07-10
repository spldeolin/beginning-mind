package com.spldeolin.beginningmind.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 本项目除了UrlForwardToExceptionController、TestController、SignController以外，
 * 每个开发者编写的请求方法都需要权限才能访问
 * <p>
 * 通过指定本注解，对每个权限进行额外的修饰说明。
 * <p>
 * 通过指定displayName属性，来声明“我展示给用户的权限名是xxx”；
 * <p>
 * 通过指定isRequiredBy属性，来声明“显示xxx菜单的前提是用户有这个权限”； TODO 设计
 * <p>
 * 遗漏该注解的请求方法，将会以控制器路由+方法路由作为其缺省权限名。
 */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    String display();

}
