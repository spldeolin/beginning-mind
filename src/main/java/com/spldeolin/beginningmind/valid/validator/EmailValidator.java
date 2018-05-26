package com.spldeolin.beginningmind.valid.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.annotation.Email;

/**
 * “E-Mmail”校验器
 *
 * @author Deolin
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(value);
    }

}
