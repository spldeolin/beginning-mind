package com.spldeolin.beginningmind.core.doc.aspect;

import java.lang.reflect.Field;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import com.spldeolin.beginningmind.core.doc.JavaSourceHolder;
import springfox.documentation.spi.service.contexts.ApiListingContext;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.RequestMappingContext;
import springfox.documentation.swagger.web.SwaggerApiListingReader;

/**
 * 增强Springfox提取@Api#description的功能，重写为利用qdox提取Javadoc
 *
 * @author Deolin 2019-08-17
 * @see SwaggerApiListingReader#apply(ApiListingContext)
 */
@Aspect
@Component
public class OperationSummaryReaderAspect {

    @Autowired
    private JavaSourceHolder srcHolder;

    @Pointcut("execution(* springfox.documentation.swagger.readers.operation.OperationSummaryReader.apply"
            + "(springfox.documentation.spi.service.contexts.OperationContext))")
    public void apply() {
    }

    @Around("apply()")
    public void around(ProceedingJoinPoint point) throws Throwable {
        OperationContext operation = (OperationContext) point.getArgs()[0];

        Field field = Objects.requireNonNull(ReflectionUtils.findField(OperationContext.class, "requestContext"));
        field.setAccessible(true);
        RequestMappingContext req = (RequestMappingContext) ReflectionUtils.getField(field, operation);

        point.proceed(point.getArgs());
        // TODO 获取req关于这个handler的全限定名+参数类型列表（考虑方法重载），通过获取到的组合信息，从srcHolder获取唯一的那个JavaMethod
    }

}
