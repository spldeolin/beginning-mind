package com.spldeolin.beginningmind.valid.validator;

import java.util.Collection;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.annotation.ElementsNonNull;

/**
 * “所有元素都不为null”校验器
 *
 * @author Deolin
 */
public class ElementsNonNullValidator implements ConstraintValidator<ElementsNonNull, Collection<?>> {

    @Override
    public void initialize(ElementsNonNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        // 所有元素都不为null
        return value.stream().allMatch(Objects::nonNull);
    }

}
