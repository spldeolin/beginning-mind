package com.spldeolin.beginningmind.app.valid.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.spldeolin.beginningmind.app.valid.validator.MobileValidator;

/**
 * 确保手机号是有效的
 *
 * <pre>
 * 支持类型：String
 * 规则：11位数字，以1开头
 * </pre>
 *
 * @author Deolin 2018-05-23
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MobileValidator.class})
public @interface Mobile {

    String message() default "不是有效的手机号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
