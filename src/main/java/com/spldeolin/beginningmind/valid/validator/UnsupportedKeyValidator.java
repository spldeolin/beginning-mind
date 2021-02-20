package com.spldeolin.beginningmind.valid.validator;

import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.annotation.UnsupportedKey;

/**
 * 校验器：确保值在可选范围内
 *
 * @author Deolin 2019-10-29
 */
public class UnsupportedKeyValidator implements ConstraintValidator<UnsupportedKey, Map<String, ?>> {

    private String[] unsupportedKeys;

    @Override
    public void initialize(UnsupportedKey constraintAnnotation) {
        unsupportedKeys = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Map<String, ?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (String unsupportedKey : unsupportedKeys) {
            if (value.containsKey(unsupportedKey)) {
                return false;
            }
        }
        return true;
    }

}
