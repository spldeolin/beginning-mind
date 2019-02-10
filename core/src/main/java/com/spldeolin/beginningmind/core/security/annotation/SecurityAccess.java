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
        SIGN,
        SIGN_AND_AUTH,
        TOKEN
    }

}
