package com.spldeolin.beginningmind.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 除了ErrorForwardController、SignController、TestController以外，每个开发者编写的请求方法都需要权限才能访问
 * <p>
 * 通过为请求方法指定本注解，来声明“这是方法被请求之前需要鉴权”
 * <p>
 * 通过指定displayName属性，来声明“这个接口展示给用户的名称是xxx”；
 * <p>
 * 通过指定menuId属性，来声明“这个接口属于xxx菜单”；
 * <p>
 * 通过指定mustHave属性，来声明“这是个底层接口，所有用户无条件拥有”；
 *
 * @author Deolin 2018/8/4
 */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {

    /**
     * POST类请求展示给用户，必填；
     * <p>
     * GET类请求是不展示给用户的，非必填
     */
    String display() default "";

    /**
     * 参照`menu`表来指定该属性，必填
     */
    long menuId();

    /**
     * 指定是否所有登录者都应该拥有该接口的权限
     * <p>
     * 如果该接口的权限不需要被授予，不属于任何菜单（如上传图片等），那么需要指定mustHave为true
     * <p>
     * 当需要指定mustHave为true时，display非必填，menuId可以指定为Permission.WHAT_EVER
     * <p>
     */
    boolean mustHave() default false;

    long WHAT_EVER = Long.MAX_VALUE;

}
