package com.spldeolin.beginningmind.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;

/**
 * Mapper切面
 * <pre>
 * 前置：TODO 暂无
 * 后置：无
 * </pre>
 *
 * @author Deolin 2018/12/24
 */
@Component
@Aspect
@Log4j2
public class MapperAcpect {

    /**
     * Spring可扫描的，BaseMapper的所有派生类
     */
    @Pointcut("this(com.baomidou.mybatisplus.core.mapper.BaseMapper)")
    public void daoMapper() {
    }

    @Around("daoMapper()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.info(1111);
        return point.proceed(point.getArgs());
    }

}
