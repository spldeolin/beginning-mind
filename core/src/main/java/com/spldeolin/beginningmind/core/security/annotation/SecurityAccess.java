package com.spldeolin.beginningmind.core.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Deolin 2019-02-08
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecurityAccess {

    AccessMode value() default AccessMode.SIGN;

    enum AccessMode {
        // 必须登录才能访问被声明的请求
        SIGN,

        // 必须登录且登录者拥有被声明的请求的权限，才能访问
        SIGN_AND_AUTH,

        // 必须在请求中带上正确的token，才能访问
        TOKEN,

        // 畅通无阻，与没有声明@SecutiyAccess等价，将Mode设置为UNIMPEDED可以起到强调的作用
        UNIMPEDED
    }

}
