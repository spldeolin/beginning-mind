package com.spldeolin.beginningmind.restful.aop;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.restful.dto.ControllerInfo;
import com.spldeolin.beginningmind.util.RequestContextUtil;
import com.spldeolin.beginningmind.util.StringRandomUtil;
import lombok.extern.log4j.Log4j2;

/**
 * 切面：控制器解析
 *
 * @author Deolin
 */
@Component
@Aspect
@Log4j2
public class AnalyzeAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
    public void controllerMethod() {}

    @Before("controllerMethod()")
    public void before(JoinPoint point) {
        // 控制器对象
        Object controllerTarget = point.getTarget();
        // 请求方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        // 请求方法的参数名
        String[] paramNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
        // 请求方法的参数值
        Object[] paramValues = point.getArgs();
        // 标识
        String insignia = StringRandomUtil.generateEnNum(6);

        RequestContextUtil.setControllerInfo(
                new ControllerInfo().setControllerTarget(controllerTarget).setMethod(method).setParameterNames(
                        paramNames).setParameterValues(paramValues).setInsignia(insignia));
    }

}
