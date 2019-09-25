package com.spldeolin.beginningmind.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.filter.dto.MappedCallDTO;
import com.spldeolin.beginningmind.filter.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.util.WebContext;

/**
 * Mapper切面
 * <pre>
 * 前置：让请求的mapper调用次数自增1
 * 后置：无
 * </pre>
 *
 * @author Deolin 2018/12/24
 */
@Component
@Aspect
public class MapperAspect {

    /**
     * Spring可扫描的，BaseMapper的所有派生类
     */
    @Pointcut("this(com.baomidou.mybatisplus.core.mapper.BaseMapper)")
    public void mapper() {
    }

    @Around("mapper()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] parameterValues = point.getArgs();

        long start = System.currentTimeMillis();
        Object result = point.proceed(parameterValues);
        long end = System.currentTimeMillis();

        String className = point.getTarget().getClass().getInterfaces()[0].getSimpleName();
        String methodName = ((MethodSignature) point.getSignature()).getMethod().getName();
        MappedCallDTO dto = new MappedCallDTO(className + "." + methodName, end - start);

        RequestTrackDTO track = WebContext.getRequestTrack();
        if (track != null) {
            track.getMapperCalls().add(dto);
        }

        return result;
    }

}
