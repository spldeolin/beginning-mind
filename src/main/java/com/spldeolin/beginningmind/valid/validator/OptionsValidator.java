package com.spldeolin.beginningmind.valid.validator;

import java.util.stream.DoubleStream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.annotation.Options;

/**
 * 校验器：确保值在可选范围内
 *
 * @author Deolin 2019-10-29
 */
public class OptionsValidator implements ConstraintValidator<Options, Number> {

    private double[] optionValues;

    @Override
    public void initialize(Options constraintAnnotation) {
        optionValues = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        return DoubleStream.of(optionValues).anyMatch(x -> x == value.doubleValue());
    }

}
