package com.spldeolin.beginningmind.extension.aspect;

import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.util.StringRandomUtils;

/**
 * @author Deolin 2019-05-12
 */
@Component
@Aspect
public class ScheduledAspect {

    /**
     * Spring可扫描的，所有类的，声明了@Scheduled的方法
     */
    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledMethod() {
    }

    @Around("scheduledMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        ThreadContext.put("insignia", "[" + StringRandomUtils.generateLegibleEnNum(6) + "]");

        Object result = point.proceed(point.getArgs());

        ThreadContext.remove("async");
        return result;
    }

}
