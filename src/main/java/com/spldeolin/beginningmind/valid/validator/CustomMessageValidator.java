package com.spldeolin.beginningmind.valid.validator;

import java.lang.annotation.Annotation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Deolin 2020-07-25
 */
public abstract class CustomMessageValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    protected void overwriteMessage(ConstraintValidatorContext context, String customMessage) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
    }

}