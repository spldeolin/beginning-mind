package com.spldeolin.beginningmind.core.valid.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import com.spldeolin.beginningmind.core.valid.annotation.Option;

/**
 * “文本可选项”校验器
 *
 * @author Deolin
 */
public class OptionValidator implements ConstraintValidator<Option, CharSequence> {

    private String[] options;

    private boolean ignoreCase;

    @Override
    public void initialize(Option constraintAnnotation) {
        options = constraintAnnotation.value();
        ignoreCase = constraintAnnotation.ignoreCase();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value) || options.length == 0) {
            return true;
        }
        String string = value.toString();
        for (String option : options) {
            if (ignoreCase) {
                if (option.equalsIgnoreCase(string)) {
                    return true;
                }
            } else {
                if (option.equals(string)) {
                    return true;
                }
            }
        }
        return false;
    }

}
