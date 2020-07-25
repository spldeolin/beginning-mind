package com.spldeolin.beginningmind.valid.validator;

import java.util.Collection;
import java.util.Objects;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.annotation.NotEmptyEx;

/**
 * 校验器：确保Collection对象不能为empty，并且内部元素均不能为null
 *
 * @author Deolin 2020-07-25
 * @see NotEmptyEx
 */
public class NotEmptyExValidator extends CustomMessageValidator<NotEmptyEx, Collection<?>> {

    @Override
    public void initialize(NotEmptyEx notNullEx) {
    }

    @Override
    public boolean isValid(Collection<?> c, ConstraintValidatorContext context) {
        if (c == null) {
            overwriteMessage(context, "不能为空");
            return false;
        }
        if (c.size() == 0) {
            overwriteMessage(context, "必须有元素");
            return false;
        }
        if (c.stream().anyMatch(Objects::isNull)) {
            overwriteMessage(context, "不能有为空的元素");
            return false;
        }
        return true;
    }

}
