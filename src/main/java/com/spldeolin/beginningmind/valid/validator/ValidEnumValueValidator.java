package com.spldeolin.beginningmind.valid.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.ValidityInterpretable;
import com.spldeolin.beginningmind.valid.annotation.ValidEnumValue;
import lombok.extern.log4j.Log4j2;

/**
 * 有效枚举值
 *
 * @author Deolin 2019-10-29
 */
@Log4j2
public class ValidEnumValueValidator implements ConstraintValidator<ValidEnumValue, Integer> {

    private ValidityInterpretable validityInterpretableEnum;

    private boolean isEmpty;

    @Override
    public void initialize(ValidEnumValue constraintAnnotation) {
        ValidityInterpretable[] enumConstants = constraintAnnotation.enumType().getEnumConstants();
        if (enumConstants.length == 0) {
            isEmpty = true;
        }
        validityInterpretableEnum = enumConstants[0];
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (isEmpty) {
            return false;
        }
        return validityInterpretableEnum.isValid(value);
    }

}
