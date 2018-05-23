package com.spldeolin.beginningmind.valid.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.annotation.Mobile;

/**
 * “手机号”校验器
 *
 * @author Deolin
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    private boolean nullable;

    @Override
    public void initialize(Mobile constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return nullable;
        }
        // 11位数字，以1开头
        return value.length() == 11 && value.startsWith("1");
    }

}
