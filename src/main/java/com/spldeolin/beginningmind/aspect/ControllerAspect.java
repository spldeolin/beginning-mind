package com.spldeolin.beginningmind.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import com.spldeolin.beginningmind.aspect.annotation.PageNo;
import com.spldeolin.beginningmind.aspect.annotation.PageSize;
import com.spldeolin.beginningmind.aspect.dto.ControllerInfo;
import com.spldeolin.beginningmind.aspect.dto.Invalid;
import com.spldeolin.beginningmind.aspect.exception.ExtraInvalidException;
import com.spldeolin.beginningmind.cache.RedisCache;
import com.spldeolin.beginningmind.config.BeginningMindProperties;
import com.spldeolin.beginningmind.config.SessionConfig;
import com.spldeolin.beginningmind.controller.RedirectController;
import com.spldeolin.beginningmind.util.RequestContextUtil;
import com.spldeolin.beginningmind.util.StringRandomUtil;
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
    private BeginningMindProperties properties;

    @Autowired
    private RedisCache redisCache;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
    public void controllerMethod() {}

    @Pointcut("@within(org.springframework.web.bind.annotation.RestControllerAdvice) && @annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void exceptionHandler() {}

    @Around("controllerMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // HTTP-404的请求不做任何切面处理
        if (RedirectController.ERROR_MAPPING.equals(RequestContextUtil.request().getRequestURI())) {
            return point.proceed(point.getArgs());
        }
        // 解析切点
        ControllerInfo controllerInfo = analyzePoint(point);
        RequestContextUtil.setControllerInfo(controllerInfo);
        // 开始日志
        logBefore(controllerInfo);
        // 检查会话是否被击杀
        checkKilled();
        // 刷新会话
        reflashSessionExpire();
        // 拓展注解处理
        List<Invalid> invalids = handleExtraAnnotation(controllerInfo);
        if (invalids.size() > 0) {
            throw new ExtraInvalidException().setInvalids(invalids);
        } else {
            // 执行切点
            Object requestResult = point.proceed(controllerInfo.getParameterValues());
            // 结束日志
            logAfter(controllerInfo, requestResult);
            return requestResult;
        }

    }

    @AfterReturning(value = "exceptionHandler()", returning = "requestResult")
    public void afterReturning(Object requestResult) {
        ControllerInfo controllerInfo = RequestContextUtil.getControllerInfo();
        // 未进入解析切面的异常，开始日志是不会有的，也没必要打印返回日志（如body不可读异常）
        if (controllerInfo == null) {
            return;
        }
        // 异常日志
        logThrowing(controllerInfo, requestResult);
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
                StringRandomUtil.generateEnNum(6)).build();
        return controllerInfo;
    }

    private void logBefore(ControllerInfo controllerInfo) {
        HttpServletRequest request = RequestContextUtil.request();
        log.info("收到请求。(" + controllerInfo.getInsignia() + ")");
        log.info("[协议] URL：" + request.getRequestURI());
        log.info("[协议] 动词：" + request.getMethod());
        log.info("[Java] 控制器类名：" + controllerInfo.getControllerTarget().getClass().getSimpleName());
        log.info("[Java] 请求方法名：" + controllerInfo.getMethod().getName());
        String[] parameterNames = controllerInfo.getParameterNames();
        Object[] parameterValues = controllerInfo.getParameterValues();
        for (int i = 0; i < parameterNames.length; i++) {
            log.info("[Java] 请求方法参数" + parameterNames[i] + "：" + parameterValues[i]);
        }
        log.info("开始处理...");
    }

    private void checkKilled() {
        if (redisCache.getCache("killed:session:" + RequestContextUtil.session().getId(), String.class) != null) {
            SecurityUtils.getSubject().logout();
        }
    }

    private void reflashSessionExpire() {
        HttpSession session = RequestContextUtil.session();
        session.setMaxInactiveInterval(SessionConfig.SESSION_EXPIRE_SECONDS);
    }

    private void logAfter(ControllerInfo controllerInfo, Object requestResult) {
        log.info("...处理完毕");
        log.info("[Java] 请求方法返回值：" + requestResult);
        log.info("返回响应。(" + controllerInfo.getInsignia() + ")");
    }

    private void logThrowing(ControllerInfo controllerInfo, Object requestResult) {
        log.info("...处理中断");
        log.info("统一异常处理返回值：" + requestResult);
        log.info("返回响应。" + RequestContextUtil.getControllerInfo().getInsignia());
    }

    private List<Invalid> handleExtraAnnotation(ControllerInfo controllerInfo) {
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
                // 处理PageNo注解
                if (annotation instanceof PageNo) {
                    String pageNoParamName = properties.getPageNoParamName();
                    if (StringUtils.isBlank(pageNoParamName)) {
                        pageNoParamName = "page_no";
                    }
                    parameterValue = handlePageNo(
                            RequestContextUtil.request().getParameter(pageNoParamName));
                }
                // 处理PageSize注解
                if (annotation instanceof PageSize) {
                    String pageSizeParamName = properties.getPageSizeParamName();
                    if (StringUtils.isBlank(pageSizeParamName)) {
                        pageSizeParamName = "page_size";
                    }
                    parameterValue = handlePageSize(annotation,
                            RequestContextUtil.request().getParameter(pageSizeParamName));
                }
                // 处理RequireId注解
                //if (annotation instanceof RequireId) {
                //    handleRequireId(parameterValue, invalids);
                //}
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
     * 处理PageNo注解
     *
     * @param urlParamValue 未绑定的URL参数
     * @return 处理后的值
     */
    private Object handlePageNo(String urlParamValue) {
        // 不是数字
        if (!NumberUtils.isCreatable(urlParamValue)) {
            return 1;
        }
        int value = Integer.parseInt(urlParamValue);
        // 超出范围
        if (value < 1) {
            return 1;
        }
        return value;
    }

    /**
     * 处理PageSize注解
     *
     * @param annotation PageSize注解对象
     * @param urlParamValue PageSize修饰的参数的值
     * @return 处理后的值
     */
    private Object handlePageSize(Annotation annotation, String urlParamValue) {
        // 不是数字
        if (!NumberUtils.isCreatable(urlParamValue)) {
            return ((PageSize) annotation).defaultSize();
        }
        int limit = ((PageSize) annotation).limit();
        int value = Integer.parseInt(urlParamValue);
        // 超出范围
        if (value < 1) {
            return 1;
        }
        // 超出范围
        if (value > limit) {
            return limit;
        }
        return value;
    }

    /**
     * 处理RequireId注解
     *
     * @param parameterValue RequireId修饰的参数的值
     * @param invalids 校验未通过的信息
     */
//    private void handleRequireId(Object parameterValue, List<Invalid> invalids) {
//        Class<?> clazz = parameterValue.getClass();
//        Field id = ReflectionUtils.findField(clazz, "id");
//        if (id != null) {
//            id.setAccessible(true);
//            if (ReflectionUtils.getField(id, parameterValue) == null) {
//                invalids.add(Invalid.builder().name("id").value(null).cause("不能为空").build());
//            }
//        }
//    }

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
