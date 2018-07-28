package com.spldeolin.beginningmind.core.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.ThreadContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.api.EnsureStringFieldsTrimmed;
import com.spldeolin.beginningmind.core.aspect.dto.ControllerInfo;
import com.spldeolin.beginningmind.core.aspect.dto.Invalid;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.aspect.exception.ExtraInvalidException;
import com.spldeolin.beginningmind.core.config.SessionConfig;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.model.RequestLog;
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
        ControllerInfo controllerInfo = analyzePoint(point);
        RequestContextUtils.setControllerInfo(controllerInfo);
        // 设置Log MDC
        setSignerLogMDC();
        // 开始日志
        RequestLog requestLog = logBefore(controllerInfo);
        // 检查登录者是否被踢出
        if (isKilled()) {
            throw new UnsignedException("已被管理员请离，请重新登录");
        }
        // 刷新会话
        reflashSessionExpire();
        // 为TrimmedStringFields类执行trimStringFields
        trimStringFields(controllerInfo);
        // 解析注解，做一些额外处理
        List<Invalid> invalids = handleAnnotations(controllerInfo);
        if (invalids.size() > 0) {
            throw new ExtraInvalidException().setInvalids(invalids);
        }
        // 记录开始时间
        long proceedAt = System.currentTimeMillis();
        // 执行切点
        Object data = point.proceed(controllerInfo.getParameterValues());
        // 结束日志
        logAfter(requestLog, data, proceedAt);
        // 清除MDC
        removeSignerLogMDC();
        return data;
    }

    @AfterReturning(value = "exceptionHandler()", returning = "requestResult")
    public void afterReturning(RequestResult requestResult) {
        ControllerInfo controllerInfo = RequestContextUtils.getControllerInfo();
        // 未进入解析切面的异常，开始日志是不会有的，也没必要打印返回日志（如body不可读异常）
        if (controllerInfo != null) {
            // 异常日志
            logThrowing(controllerInfo, requestResult);
            // 清除MDC
            removeSignerLogMDC();
        }
        // 确保本线程的Log MDC被清除
        removeSignerLogMDC();
    }

    private ControllerInfo analyzePoint(ProceedingJoinPoint point) {
        // 控制器对象
        Object controllerTarget = point.getTarget();
        // 请求方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        // 请求方法的参数名
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
        // 请求方法的参数值
        Object[] parameterValues = point.getArgs();
        // 生成请求标识，构造ControllerInfo对象
        ControllerInfo controllerInfo = ControllerInfo.builder().controllerTarget(controllerTarget).method(
                method).parameterNames(parameterNames).parameterValues(parameterValues).insignia(
                StringRandomUtils.generateEnNum(6)).build();
        return controllerInfo;
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

    private RequestLog logBefore(ControllerInfo controllerInfo) {
        HttpServletRequest request = RequestContextUtils.request();
        RequestLog requestLog = RequestLog.builder().loggedAt(LocalDateTime.now()).build();
        return requestLog.wireControllerInfo(controllerInfo).wireHttpServletRequest(request);
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

    private void trimStringFields(ControllerInfo controllerInfo) {
        for (Object parameterValue : controllerInfo.getParameterValues()) {
            if (parameterValue instanceof EnsureStringFieldsTrimmed) {
                EnsureStringFieldsTrimmed ensureStringFieldsTrimmed = (EnsureStringFieldsTrimmed) parameterValue;
                ensureStringFieldsTrimmed.trimStringFields();
            }
        }
    }

    private void logAfter(RequestLog requestLog, Object dataObject, long proceedAt) {
        requestLog.setProcessingMilliseconds(System.currentTimeMillis() - proceedAt);
        requestLog.setJavaReturn(ensureRequestResult(dataObject));
        // 存入mongo
        mongoTemplate.save(requestLog);
    }

    private RequestResult ensureRequestResult(Object object) {
        if (object instanceof RequestResult) {
            return (RequestResult) object;
        }
        return RequestResult.success(object);
    }

    private void logThrowing(ControllerInfo controllerInfo, RequestResult requestResult) {
        log.info("...处理中断");
        log.info("统一异常处理返回值：" + requestResult);
        log.info("返回响应。" + controllerInfo.getInsignia());
    }

    private List<Invalid> handleAnnotations(ControllerInfo controllerInfo) {
        List<Invalid> invalids = new ArrayList<>();
        Annotation[][] annotationsEachParams = controllerInfo.getMethod().getParameterAnnotations();
        String[] parameterNames = controllerInfo.getParameterNames();
        Object[] parameterValues = controllerInfo.getParameterValues();
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