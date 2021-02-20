package com.spldeolin.beginningmind.valid.validator;

import java.nio.file.Path;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.spldeolin.beginningmind.valid.annotation.IsDirectory;

/**
 * 校验器：确保目标是一个目录
 *
 * @author Deolin 2021-02-20
 */
public class PathIsDirectoryValidator implements ConstraintValidator<IsDirectory, Path> {

    @Override
    public void initialize(IsDirectory constraintAnnotation) {
    }

    @Override
    public boolean isValid(Path value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.toFile().isDirectory();
    }

}