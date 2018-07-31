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
import com.spldeolin.beginningmind.core.api.valid.validator.OptionValidator;

/**
 * “可选项”校验用注解
 * <pre>
 * 支持类型：CharSequence
 * 规则：必须是{value}的其中之一，才能通过校验。如果不指定{value}，则本注解不会产生任何作用
 * </pre>
 */
@Documented
@Constraint(validatedBy = {OptionValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Option {

    String message() default "不是可选项";

    Class<?>[] groups() default {};

    String[] value() default {};

    boolean ignoreCase() default false;

    Class<? extends Payload>[] payload() default {};

}
