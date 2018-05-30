package com.spldeolin.beginningmind.core.valid.validator;

import java.lang.reflect.Field;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.ReflectionUtils;
import com.spldeolin.beginningmind.core.valid.annotation.Require;

/**
 * “必选项”校验器
 *
 * @author Deolin
 */
public class RequireValidator implements ConstraintValidator<Require, Object> {

    private String[] fieldNames;

    @Override
    public void initialize(Require constraintAnnotation) {
        fieldNames = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null || fieldNames == null || fieldNames.length == 0) {
            return true;
        }
        Class clazz = value.getClass();
        for (String fieldName : fieldNames) {
            Field field = ReflectionUtils.findField(clazz, fieldName);
            if (field == null) {
                throw new RuntimeException("类" + clazz + "不存在字段" + fieldName);
            }
            field.setAccessible(true);
            Object fieldValue = ReflectionUtils.getField(field, value);
            if (fieldValue == null) {
                return false;
            }
        }
        return true;
    }

}
