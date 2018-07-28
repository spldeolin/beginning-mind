package com.spldeolin.beginningmind.core.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.ThreadContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.api.EnsureStringFieldsTrimmed;
import com.spldeolin.beginningmind.core.aspect.dto.Invalid;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrack;
import com.spldeolin.beginningmind.core.aspect.exception.ExtraInvalidException;
import com.spldeolin.beginningmind.core.config.SessionConfig;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.security.exception.UnsignedException;
import com.spldeolin.beginningmind.core.util.RequestContextUtils;
import com.spldeolin.beginningmind.core.util.Sessions;
import com.spldeolin.beginningmind.core.util.Signer;
import com.spldeolin.beginningmind.core.util.string.StringRandomUtils;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层切面
 * <pre>
 * 基础处理：控制器解析、额外注解处理、日志处理
 * </pre>
 */
@Component
@Aspect
@Log4j2
public class ControllerAspect {

    @Autowired
    private CoreProperties properties;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

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
        // 解析切点
        RequestTrack requestTrack = analyzePoint(point);
        RequestContextUtils.setRequestTrack(requestTrack);
        // 设置Log MDC
        setSignerLogMDC();
        // 检查登录者是否被踢出
        if (isKilled()) {
            throw new UnsignedException("已被管理员请离，请重新登录");
        }
        // 刷新会话
        reflashSessionExpire();
        // 为TrimmedStringFields类执行trimStringFields
        trimStringFields(requestTrack);
        // 解析注解，做一些额外处理
        List<Invalid> invalids = handleAnnotations(requestTrack);
        if (invalids.size() > 0) {
            throw new ExtraInvalidException().setInvalids(invalids);
        }
        // 执行切点
        requestTrack.setProcessedAt(System.currentTimeMillis());
        Object data = point.proceed(requestTrack.getParameterValues());
        // 请求成功时保存日志
        logAfter(requestTrack, data);
        // 清除Log MDC
        removeSignerLogMDC();
        return data;
    }

    @AfterReturning(value = "exceptionHandler()", returning = "requestResult")
    public void afterReturning(RequestResult requestResult) {
        RequestTrack requestTrack = RequestContextUtils.getRequestTrack();
        // 未进入解析切面的异常，请求是没有RequestTrack的，并在这里的joinPoint对象也不时Controller，所以无法记录日志
        if (requestTrack != null) {
            logThrowing(requestTrack, requestResult);
        }
        // 清除Log MDC
        removeSignerLogMDC();
    }

    private RequestTrack analyzePoint(JoinPoint point) {
        HttpServletRequest request = RequestContextUtils.request();
        RequestTrack track = new RequestTrack();
        Method requestMethod = ((MethodSignature) point.getSignature()).getMethod();
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(requestMethod);
        Object[] parameterValues = point.getArgs();
        track.setMethod(requestMethod);
        track.setParameterNames(parameterNames);
        track.setParameterValues(parameterValues);

        Map<String, String> parameterObjects = Maps.newHashMap();
        for (int i = 0; i < parameterNames.length; i++) {
            parameterObjects.put(parameterNames[i], parameterValues[i].toString());
        }
        if (parameterObjects.size() == 0) {
            parameterObjects = null;
        }
        track.setInsignia(StringRandomUtils.generateLegibleEnNum(6));
        track.setInsertedAt(LocalDateTime.now());
        track.setHttpUrl(request.getRequestURI());
        track.setHttpMethod(request.getMethod());
        track.setController(point.getTarget().getClass().getSimpleName());
        track.setRequestMethod(requestMethod.getName());
        track.setParameterObjects(parameterObjects);
        return track;
    }

    private void setSignerLogMDC() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            ThreadContext.put(CoupledConstant.LOG_PATTERN_PARAM,
                    "[" + Signer.current().getSecurityUser().getUsername() + "]");
        }
    }

    private void removeSignerLogMDC() {
        ThreadContext.remove(CoupledConstant.LOG_PATTERN_PARAM);
    }

    /**
     * @return true：已被踢出，false：未被踢出
     */
    private boolean isKilled() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            String cacheKey = "killed:session:" + Sessions.session().getId();
            // 被踢出
            if (redisTemplate.opsForValue().get(cacheKey) != null) {
                // 登出
                subject.logout();
                // 删除标识
                redisTemplate.delete(cacheKey);
                return true;
            }
        }
        return false;
    }

    private void reflashSessionExpire() {
        HttpSession session = Sessions.session();
        if (properties.isDebug()) {
            session.setMaxInactiveInterval(86400);
        } else {
            session.setMaxInactiveInterval(SessionConfig.SESSION_EXPIRE_SECONDS);
        }
    }

    private void trimStringFields(RequestTrack requestTrack) {
        for (Object parameterValue : requestTrack.getParameterValues()) {
            if (parameterValue instanceof EnsureStringFieldsTrimmed) {
                EnsureStringFieldsTrimmed ensureStringFieldsTrimmed = (EnsureStringFieldsTrimmed) parameterValue;
                ensureStringFieldsTrimmed.trimStringFields();
            }
        }
    }

    private void logAfter(RequestTrack track, Object dataObject) {
        track.setProcessingMilliseconds(System.currentTimeMillis() - track.getProcessedAt());
        track.setRequestResult(ensureRequestResult(dataObject));
        // 存入mongo
        mongoTemplate.save(track);
    }

    private RequestResult ensureRequestResult(Object object) {
        if (object instanceof RequestResult) {
            return (RequestResult) object;
        }
        return RequestResult.success(object);
    }

    private void logThrowing(RequestTrack track, RequestResult requestResult) {
        track.setProcessingMilliseconds(System.currentTimeMillis() - track.getProcessedAt());
        track.setRequestResult(requestResult);
        // 存入MongoDB
        mongoTemplate.save(track);
    }

    private List<Invalid> handleAnnotations(RequestTrack requestTrack) {
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