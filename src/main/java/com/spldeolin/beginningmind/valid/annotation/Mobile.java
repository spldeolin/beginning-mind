package com.spldeolin.beginningmind.valid.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.spldeolin.beginningmind.valid.validator.MobileValidator;

/**
 * “手机号”校验注解
 * <pre>
 * 支持类型：String
 * 规则：11位数字，以1开头
 * </pre>
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MobileValidator.class})
public @interface Mobile {

    String message() default "不是正确的手机号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
