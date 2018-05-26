package com.spldeolin.beginningmind.valid.annotation;

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
import com.spldeolin.beginningmind.valid.validator.EmailValidator;

/**
 * “E-Mail”校验注解
 * <pre>
 * 支持类型：String
 * 规则：委托Commons Validator框架的EmailValidator类校验
 * </pre>
 */
@Documented
@Constraint(validatedBy = {EmailValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Email {

    String message() default "不是正确的E-Mail";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
