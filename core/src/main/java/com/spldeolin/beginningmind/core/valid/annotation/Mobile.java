package com.spldeolin.beginningmind.core.valid.annotation;

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
import com.spldeolin.beginningmind.core.valid.validator.MobileValidator;

/**
 * “手机号”校验注解
 * <pre>
 * 支持类型：String
 * 规则：11位数字，以1开头
 * </pre>
 */
@Documented
@Constraint(validatedBy = {MobileValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Mobile {

    String message() default "不是正确的手机号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
