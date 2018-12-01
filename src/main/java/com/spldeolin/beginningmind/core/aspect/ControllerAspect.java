package com.spldeolin.beginningmind.core.aspect;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.common.base.Stopwatch;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.aspect.dto.Invalid;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.core.aspect.exception.ExtraInvalidException;
import com.spldeolin.beginningmind.core.config.SessionConfig;
import com.spldeolin.beginningmind.core.filter.GlobalFilter;
import com.spldeolin.beginningmind.core.security.util.Signer;
import com.spldeolin.beginningmind.core.service.RequestTrackService;
import com.spldeolin.beginningmind.core.service.SignService;
import com.spldeolin.beginningmind.core.service.impl.SignServiceImpl;
import com.spldeolin.beginningmind.core.util.Requests;
import com.spldeolin.beginningmind.core.util.Sessions;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层切面
 * <pre>
 * 基础处理：控制器解析、额外注解处理、日志处理
 * </pre>
 *
 * @author Deolin
 */
@Component
@Aspect
@Log4j2
public class ControllerAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RequestTrackService requestTrackService;

    @Autowired
    private SignService signService;

    @Autowired
    private GlobalFilter globalFilter;

    /**
     * Spring可扫描的， com.spldeolin.beginningmind.core.controller包及其子包下的， 声明了@RestController注解的类， 中的所有方法
     */
    @Pointcut("execution(* com.spldeolin.beginningmind.core.controller..*.*(..))" +
            " && @within(org.springframework.web.bind.annotation.RestController)")
    public void controllerMethod() {
    }

    /**
     * Spring可扫描的， 声明了@RestControllerAdvice注解的类， 中声明了ExceptionHandler注解的， 所有方法
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestControllerAdvice)" +
            " && @annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void exceptionHandler() {
    }

    @Around("controllerMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = Requests.request();

        // 解析切点
        RequestTrackDTO requestTrack = globalFilter.getRequestTrackContext().get();

        // 检查登录者是否被踢出
        Long signedUserId = Signer.isSigning() ? Signer.userId() : null;
        if (Signer.isSigning() && isKilled(signedUserId)) {
            signService.signOut();
            throw new ServiceException("已被请离，请重新登录");
        }

        // 刷新会话失效时间
        reflashSessionExpire();

        // TODO 刷新会话中k-v的失效时间

        // 解析注解，做一些额外处理
        List<Invalid> invalids = handleAnnotations(requestTrack);
        if (invalids.size() > 0) {
            throw new ExtraInvalidException(invalids);
        }

        // 执行切点
        requestTrack.setStopwatch(Stopwatch.createStarted());
        Object data = point.proceed(requestTrack.getParameterValues());

        // 请求成功时保存日志
        requestTrackService.completeAndSaveTrack(requestTrack, request, data);

        return data;
    }

    @AfterReturning(value = "exceptionHandler()", returning = "requestResult")
    public void afterReturning(RequestResult requestResult) {
        RequestTrackDTO track = globalFilter.getRequestTrackContext().get();
        // 未进入解析切面的异常，请求是没有RequestTrack的，并在这里的joinPoint对象也不是Controller，所以无法记录日志
        if (track != null) {
            requestTrackService.completeAndSaveTrack(track, Requests.request(), requestResult);
        }
    }

    private boolean isKilled(Long signedUserId) {
        return redisTemplate.opsForHash()
                .get(SignServiceImpl.SIGN_STATUS_BY_USER_ID + signedUserId, Sessions.session().getId()) == null;
    }

    private void reflashSessionExpire() {
        Sessions.session().setMaxInactiveInterval(SessionConfig.SESSION_EXPIRE_SECONDS);
    }

    private List<Invalid> handleAnnotations(RequestTrackDTO requestTrack) {
        List<Invalid> invalids = new ArrayList<>();
        Annotation[][] annotationsEachParams = requestTrack.getMethod().getParameterAnnotations();
        String[] parameterNames = requestTrack.getParameterNames();
        Object[] parameterValues = requestTrack.getParameterValues();
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
            if (parameterValue == null ||
                    (parameterValue instanceof String && ((String) parameterValue).length() == 0)) {
                invalids.add(Invalid.builder().name(parameterName).value(null).cause("不能为空").build());
            }
        }
    }

}