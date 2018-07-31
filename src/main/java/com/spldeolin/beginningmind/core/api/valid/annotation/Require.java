package com.spldeolin.beginningmind.core.api.valid.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.spldeolin.beginningmind.core.api.valid.validator.RequireValidator;

/**
 * “必选项”校验用注解
 * <pre>
 * 支持类型：Object
 * 规则：被声明的对象中，指定字段必须不为null。如果不指定{value}，则本注解不会产生任何作用
 * </pre>
 */
@Documented
@Constraint(validatedBy = {RequireValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Require {

    String message() default "必选项为null";

    Class<?>[] groups() default {};

    String[] value();

    Class<? extends Payload>[] payload() default {};

}
