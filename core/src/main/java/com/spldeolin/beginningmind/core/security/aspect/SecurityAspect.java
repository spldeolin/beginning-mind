package com.spldeolin.beginningmind.core.security.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.security.annotation.SecurityAccess;
import com.spldeolin.beginningmind.core.security.annotation.SecurityAccess.AccessMode;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2019-02-08
 */
@Component
@Aspect
@Log4j2
public class SecurityAspect {

    /**
     * 包名以com.spldeolin.beginningmind.开头的，
     *
     * 声明了@RestController的类，
     *
     * 中的声明了@Sign的方法
     */
    @Pointcut("execution(* com.spldeolin.beginningmind..*.*(..))"
            + "&& @within(org.springframework.web.bind.annotation.RestController)"
            + "&& @annotation(com.spldeolin.beginningmind.core.security.annotation.Access)")
    public void access() {
    }

    @Before("access()")
    public void ensureAccess(JoinPoint joinPoint) {
        Method requestMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        SecurityAccess securityAccess = requestMethod.getAnnotation(SecurityAccess.class);
        AccessMode mode = securityAccess.value();
        switch (mode) {
            case SIGN:
                // TODO 登录
                break;
            case SIGN_AND_AUTH:
                // TODO 鉴权
                break;
            case TOKEN:
                // TODO TOKEN
                break;
        }
    }

}
