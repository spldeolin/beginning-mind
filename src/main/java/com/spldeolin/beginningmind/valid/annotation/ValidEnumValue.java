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
import com.spldeolin.beginningmind.valid.ValidityInterpretable;
import com.spldeolin.beginningmind.valid.validator.ValidEnumValueValidator;

/**
 * “有效的枚举值”校验注解
 * <pre>
 * 支持类型：Integer
 * 规则：11位数字，以1开头
 * </pre>
 *
 * @author Deolin 2019-10-29
 */
@Documented
@Constraint(validatedBy = {ValidEnumValueValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface ValidEnumValue {

    String message() default "不是有效的枚举值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends ValidityInterpretable> enumType();

}
