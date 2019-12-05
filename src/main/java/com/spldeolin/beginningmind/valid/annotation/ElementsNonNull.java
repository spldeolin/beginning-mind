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
import com.spldeolin.beginningmind.valid.validator.ElementsNonNullValidator;

/**
 * 所有元素都不为null
 * <pre>
 * 支持类型：Collection
 * 规则：所有元素都不为null
 * </pre>
 *
 * @author Deolin 2019-12-05
 */
@Documented
@Constraint(validatedBy = {ElementsNonNullValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface ElementsNonNull {

    String message() default "列表中所有元素均不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
