package com.spldeolin.beginningmind.valid.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.spldeolin.beginningmind.valid.validator.OptionsValidator;

/**
 * 确保值在可选范围内
 *
 * <pre>
 * 支持类型：Number
 * </pre>
 *
 * @author Deolin 2019-10-29
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {OptionsValidator.class})
public @interface Options {

    double[] value() default {};

    String message() default "值不在可选范围内";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
