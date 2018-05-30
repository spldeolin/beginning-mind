package com.spldeolin.beginningmind.core.valid.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.core.valid.annotation.Mobile;

/**
 * “手机号”校验器
 *
 * @author Deolin
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public void initialize(Mobile constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        // 11位数字，以1开头
        return value.length() == 11 && value.startsWith("1");
    }

}
