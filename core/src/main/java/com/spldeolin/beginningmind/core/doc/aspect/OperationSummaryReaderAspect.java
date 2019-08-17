package com.spldeolin.beginningmind.core.doc.aspect;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.method.HandlerMethod;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.doc.JavaSourceHolder;
import springfox.documentation.RequestHandler;
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
        OperationContext context = (OperationContext) point.getArgs()[0];

        RequestMappingContext requestMappingContext = getFieldValue(OperationContext.class, context, "requestContext");
        RequestHandler requestHandler = getFieldValue(RequestMappingContext.class, requestMappingContext, "handler");
        HandlerMethod handlerMethod = requestHandler.getHandlerMethod();
        String methodFqName = handlerMethod.getBeanType().getName() + "." + handlerMethod.getMethod().getName();
        List<String> paramFqNames = Lists.newArrayList();
        for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
            paramFqNames.add(methodParameter.getParameterType().getName());
        }

        point.proceed(point.getArgs());

        context.operationBuilder().summary(srcHolder.getMethodComment(methodFqName, paramFqNames));
    }

    @SuppressWarnings("unchecked")
    private <T> T getFieldValue(Class<?> targetType, Object target, String fieldName) {
        Field field = Objects.requireNonNull(ReflectionUtils.findField(targetType, fieldName));
        field.setAccessible(true);
        return (T) ReflectionUtils.getField(field, target);
    }

}
