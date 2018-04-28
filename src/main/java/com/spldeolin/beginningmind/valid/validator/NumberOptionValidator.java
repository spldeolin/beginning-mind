package com.spldeolin.beginningmind.valid.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.annotation.NumberOption;

/**
 * “数字可选项”校验器
 *
 * @author Deolin
 */
public class NumberOptionValidator implements ConstraintValidator<NumberOption, Number> {

    private double[] options;

    @Override
    public void initialize(NumberOption constraintAnnotation) {
        options = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value == null || options == null || options.length == 0) {
            return true;
        }
        for (double option : options) {
            if (value.equals(option)) {
                return true;
            }
        }
        return false;
    }

}
