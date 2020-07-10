package com.spldeolin.beginningmind.security.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.CoreProperties;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;
import com.spldeolin.beginningmind.security.PermissionChecker;
import com.spldeolin.beginningmind.security.SignedChecker;
import com.spldeolin.beginningmind.security.TokenChecker;
import com.spldeolin.beginningmind.security.annotation.SecurityAccess;
import com.spldeolin.beginningmind.security.annotation.SecurityAccess.AccessMode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2019-02-08
 */
@Component
@Aspect
@Slf4j
public class SecurityAspect {

    @Autowired
    private SignedChecker signedChecker;

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private TokenChecker tokenChecker;

    @Autowired
    private CoreProperties coreProperties;

    /**
     * 包名以com.spldeolin.beginningmind.开头的，
     *
     * 声明了@RestController的类，
     *
     * 中的声明了@Sign的方法
     */
    @Pointcut("execution(* com.spldeolin.beginningmind..*.*(..))"
            + "&& @within(org.springframework.web.bind.annotation.RestController)"
            + "&& @annotation(com.spldeolin.beginningmind.security.annotation.SecurityAccess)")
    public void access() {
    }

    @Before("access()")
    public void ensureAccess(JoinPoint joinPoint) {
        if (!coreProperties.getEnableSecurity()) {
            return;
        }

        Method requestMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        SecurityAccess securityAccess = requestMethod.getAnnotation(SecurityAccess.class);
        AccessMode mode = securityAccess.value();
        switch (mode) {
            case SIGN:
                // 登录
                signedChecker.ensureSigned();
                break;
            case SIGN_AND_AUTH:
                // 登录 与 鉴权
                signedChecker.ensureSigned();
                permissionChecker.ensurePermission(RequestTrack.getCurrent().getRequest());
                break;
            case TOKEN:
                // TOKEN
                tokenChecker.ensureTokenCorrect(RequestTrack.getCurrent().getRequest(), requestMethod);
                break;
        }
    }

}
