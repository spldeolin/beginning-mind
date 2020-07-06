package com.spldeolin.beginningmind.extension.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import com.spldeolin.beginningmind.extension.dto.Invalid;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;
import com.spldeolin.beginningmind.extension.exception.ExtraInvalidException;
import com.spldeolin.beginningmind.util.WebContext;

/**
 * 控制层切面
 * <pre>
 * 前置：填入切点信息、注解额外校验
 * 后置：无
 * </pre>
 *
 * @author Deolin
 */
@Component
@Aspect
public class ControllerAspect {

    /**
     * 包名以com.spldeolin.beginningmind.开头的，声明了@RestController注解的类， 中的所有方法
     */
    @Pointcut("execution(* com.spldeolin.beginningmind..*.*(..))"
            + "&& @within(org.springframework.web.bind.annotation.RestController)")
    public void controllerMethod() {
    }

    @Around("controllerMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        RequestTrack requestTrack = WebContext.getRequestTrack();
        if (requestTrack == null) {
            throw new RuntimeException("获取失败，当前线程并不是Web请求线程");
        }

        // 填入切点信息
        fillJoinPointInfo(requestTrack, point);

        // 执行切点
        return point.proceed(requestTrack.getParameterValues());
    }

    private void fillJoinPointInfo(RequestTrack requestTrack, JoinPoint joinPoint) {
        Method requestMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(requestMethod);
        Object[] parameterValues = joinPoint.getArgs();
        requestTrack.setFullName(joinPoint.getTarget().getClass().getName() + "#" + requestMethod.getName());
        requestTrack.setMethod(requestMethod);
        requestTrack.setParameterNames(parameterNames);
        requestTrack.setParameterValues(parameterValues);
    }

}