package com.spldeolin.beginningmind.restful.aop;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.restful.dto.ControllerInfo;
import com.spldeolin.beginningmind.util.RequestContextUtil;
import lombok.extern.log4j.Log4j2;

/**
 * 切面：请求方法的日志打印日历
 *
 * @author Deolin
 */
@Component
@Aspect
@Log4j2
public class LogAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
    public void controllerMethod() {}

    @Pointcut("@within(org.springframework.web.bind.annotation.RestControllerAdvice) || @annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void exceptionHandler() {}

    @Around("controllerMethod()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ControllerInfo controllerInfo = RequestContextUtil.getControllerInfo();
        if (controllerInfo == null) {
            // 什么都不做
            return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        }
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

        Object requestResult = proceedingJoinPoint.proceed(parameterValues);

        log.info("...处理完毕");
        log.info("[Java] 请求方法返回值：" + requestResult);
        log.info("返回响应。(" + controllerInfo.getInsignia() + ")");
        return requestResult;
    }

    @AfterReturning(value = "exceptionHandler()", returning = "requestResult")
    public void throwing(Object requestResult) {
        ControllerInfo controllerInfo = RequestContextUtil.getControllerInfo();
        // 未进入解析切面的异常，开始日志是不会有的，也没必要打印返回日志（如body不可读异常）
        if (controllerInfo == null) {
            return;
        }
        log.info("...处理中断");
        log.info("统一异常处理返回值：" + requestResult);
        log.info("返回响应。" + RequestContextUtil.getControllerInfo().getInsignia());
    }

}
