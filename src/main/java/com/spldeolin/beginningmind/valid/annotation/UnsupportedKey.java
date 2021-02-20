package com.spldeolin.beginningmind.valid.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.spldeolin.beginningmind.valid.validator.UnsupportedKeyValidator;

/**
 * 不支持的key
 *
 * <pre>
 *  支持类型：Map&lt;String, ?>
 *  规则：被申明的Map对象中，不允许出现value中指定的key
 *  </pre>
 *
 * @author Deolin 2021-02-20
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UnsupportedKeyValidator.class)
public @interface UnsupportedKey {

    String[] value() default {};

    String message() default "不支持的key";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
