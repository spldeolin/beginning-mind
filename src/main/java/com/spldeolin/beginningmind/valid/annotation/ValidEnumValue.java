package com.spldeolin.beginningmind.valid.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.spldeolin.beginningmind.valid.ValidityInterpretableEnum;
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
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidEnumValueValidator.class})
public @interface ValidEnumValue {

    String message() default "不是有效的枚举值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends ValidityInterpretableEnum> enumType();

}
