package com.spldeolin.beginningmind.valid.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.spldeolin.beginningmind.valid.validator.NotEmptyExValidator;
import com.spldeolin.beginningmind.valid.validator.NotNullExValidator;

/**
 * 确保Collection对象不能为empty，并且内部元素均不能为null
 *
 * <pre>
 * 支持类型：Collection<?>
 * </pre>
 *
 * @author Deolin 2020-07-25
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {NotEmptyExValidator.class})
public @interface NotEmptyEx {

    String message() default "不能为空，且必须有元素，且元素均不能为null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
