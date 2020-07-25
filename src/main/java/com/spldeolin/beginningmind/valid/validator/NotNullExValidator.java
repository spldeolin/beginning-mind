package com.spldeolin.beginningmind.valid.validator;

import java.util.Collection;
import java.util.Objects;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.annotation.NotNullEx;

/**
 * 校验器：确保Collection对象不能为null，如果size()>0的话，内部元素也均不能为null
 *
 * @author Deolin 2020-07-25
 * @see NotNullEx
 */
public class NotNullExValidator extends CustomMessageValidator<NotNullEx, Collection<?>> {

    @Override
    public void initialize(NotNullEx notNullEx) {
    }

    @Override
    public boolean isValid(Collection<?> c, ConstraintValidatorContext context) {
        if (c == null) {
            overwriteMessage(context, "不能为空");
            return false;
        }
        if (c.stream().anyMatch(Objects::isNull)) {
            overwriteMessage(context, "不能有为空的元素");
            return false;
        }
        return true;
    }

}
