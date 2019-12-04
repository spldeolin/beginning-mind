package com.spldeolin.beginningmind.extension.aspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.google.common.base.Joiner;
import com.spldeolin.beginningmind.extension.dto.Invalid;
import com.spldeolin.beginningmind.extension.exception.MethodCallInvalidException;
import lombok.extern.log4j.Log4j2;

/**
 * 切面：调用非Controller组件的方法，参数检验未通过时的处理
 *
 * @author Deolin 2019-12-04
 */
@Log4j2
@Component
@Aspect
public class MethodCallValidatedAspect {

    /**
     * Spring可扫描的，声明了@Validated但未声明RestController和Controller注解的类， 中的所有方法
     */
    @Pointcut("@within(org.springframework.validation.annotation.Validated)"
            + "&& !@within(org.springframework.web.bind.annotation.RestController)"
            + "&& !@within(org.springframework.stereotype.Controller)")
    public void validatedComponent() {
    }

    @AfterThrowing(value = "validatedComponent()", throwing = "e")
    public void logError(JoinPoint point, ConstraintViolationException e) {
        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        List<Invalid> invalids = new ArrayList<>();
        for (ConstraintViolation<?> cv : cvs) {
            String location = Joiner.on(".")
                    .join(cv.getRootBeanClass().getSimpleName(), cv.getPropertyPath().toString());
            Invalid invalid = new Invalid(location, cv.getInvalidValue(), cv.getMessage());
            invalids.add(invalid);
        }
        log.error("调用方未通过组件的参数校验(args{})({})", point.getArgs(), invalids);
        throw new MethodCallInvalidException();
    }

}
