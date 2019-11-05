package com.spldeolin.beginningmind.valid.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.ValidityInterpretableEnum;
import com.spldeolin.beginningmind.valid.annotation.ValidEnumValue;

/**
 * “有效枚举值”校验器
 *
 * @author Deolin 2019-10-29
 */
public class ValidEnumValueValidator implements ConstraintValidator<ValidEnumValue, Integer> {

    private ValidityInterpretableEnum validityInterpretableEnum;

    private boolean isEmpty;

    @Override
    public void initialize(ValidEnumValue constraintAnnotation) {
        ValidityInterpretableEnum[] enumConstants = constraintAnnotation.enumType().getEnumConstants();
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
