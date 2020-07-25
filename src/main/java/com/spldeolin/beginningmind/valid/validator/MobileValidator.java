package com.spldeolin.beginningmind.valid.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.annotation.Mobile;

/**
 * 校验器：确保手机号是有效的
 *
 * @author Deolin 2018-05-23
 */
public class MobileValidator extends CustomMessageValidator<Mobile, String> {

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
