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
import com.spldeolin.beginningmind.extension.exception.ExtraInvalidException;
import com.spldeolin.beginningmind.extension.dto.RequestTrackDTO;
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
        RequestTrackDTO track = WebContext.getRequestTrack();
        if (track == null) {
            throw new RuntimeException("获取失败，当前线程并不是Web请求线程");
        }

        // 填入切点信息
        fillJoinPointInfo(track, point);

        // 注解额外校验
        List<Invalid> invalids = handleAnnotations(track);
        if (invalids.size() > 0) {
            throw new ExtraInvalidException(invalids);
        }

        // 执行切点
        return point.proceed(track.getParameterValues());
    }

    private void fillJoinPointInfo(RequestTrackDTO track, JoinPoint joinPoint) {
        Method requestMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(requestMethod);
        Object[] parameterValues = joinPoint.getArgs();
        track.setFullName(joinPoint.getTarget().getClass().getName() + "#" + requestMethod.getName());
        track.setMethod(requestMethod);
        track.setParameterNames(parameterNames);
        track.setParameterValues(parameterValues);
    }

    private List<Invalid> handleAnnotations(RequestTrackDTO track) {
        List<Invalid> invalids = new ArrayList<>();
        Annotation[][] annotationsEachParams = track.getMethod().getParameterAnnotations();
        String[] parameterNames = track.getParameterNames();
        Object[] parameterValues = track.getParameterValues();
        for (int i = 0; i < annotationsEachParams.length; i++) {
            Annotation[] annotations = annotationsEachParams[i];
            String parameterName = parameterNames[i];
            // 已绑定的参数值（副本）
            Object parameterValue = parameterValues[i];
            for (Annotation annotation : annotations) {
                // 进行RequestParam注解的空检验
                if (annotation instanceof RequestParam) {
                    handleRequestParam(annotation, parameterName, parameterValue, invalids);
                }
                parameterValues[i] = parameterValue;
            }
        }
        return invalids;
    }

    /**
     * 处理RequestParam注解
     *
     * @param annotation RequestParam注解对象
     * @param parameterName RequestParam修饰的参数的名称
     * @param parameterValue RequestParam修饰的参数的值
     * @param invalids 校验未通过的信息
     */
    private void handleRequestParam(Annotation annotation, String parameterName, Object parameterValue,
            List<Invalid> invalids) {
        if (((RequestParam) annotation).required()) {
            // 空对象
            if (parameterValue == null || (parameterValue instanceof String
                    && ((String) parameterValue).length() == 0)) {
                invalids.add(new Invalid(parameterName, null, "不能为空"));
            }
        }
    }

}