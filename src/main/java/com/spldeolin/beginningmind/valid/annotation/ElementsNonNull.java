package com.spldeolin.beginningmind.valid.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ElementsNonNullValidator.class})
public @interface ElementsNonNull {

    String message() default "列表中所有元素均不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
