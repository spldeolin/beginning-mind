package com.spldeolin.beginningmind.core.doc.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.method.HandlerMethod;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.config.JavaSourceConfig;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaType;
import springfox.documentation.RequestHandler;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.RequestMappingContext;
import springfox.documentation.swagger.readers.operation.OperationSummaryReader;

/**
 * 增强Springfox提取@ApiOperation#value的功能，重写为利用qdox提取Javadoc
 *
 * @author Deolin 2019-08-17
 * @see OperationSummaryReader#apply(springfox.documentation.spi.service.contexts.OperationContext)
 */
@Aspect
@Component
public class OperationSummaryReaderAspect {

    @Autowired
    @Qualifier(JavaSourceConfig.JAVA_CLASSES)
    private Map<String, JavaClass> javaClasses;

    @Pointcut("execution(* springfox.documentation.swagger.readers.operation.OperationSummaryReader.apply"
            + "(springfox.documentation.spi.service.contexts.OperationContext))")
    public void apply() {
    }

    @Around("apply()")
    public void around(ProceedingJoinPoint point) throws Throwable {
        OperationContext context = (OperationContext) point.getArgs()[0];

        point.proceed(point.getArgs());

        String summary = "";
        RequestMappingContext requestMappingContext = this
                .getFieldValue(OperationContext.class, context, "requestContext");
        RequestHandler requestHandler = this
                .getFieldValue(RequestMappingContext.class, requestMappingContext, "handler");
        HandlerMethod handlerMethod = requestHandler.getHandlerMethod();
        String classQualifiedName = handlerMethod.getBeanType().getName();
        String methodName = handlerMethod.getMethod().getName();
        List<JavaMethod> nameMatchedMethods = Lists.newArrayList();
        JavaClass javaClass = javaClasses.get(classQualifiedName);
        if (javaClass == null) {
            return;
        }
        for (JavaMethod method : javaClass.getMethods()) {
            if (method.getName().equals(methodName)) {
                nameMatchedMethods.add(method);
            }
        }
        if (nameMatchedMethods.size() == 1) {
            // 乐观情况，通过类全限定名+方法名直接找到唯一的一个方法
            summary = nameMatchedMethods.get(0).getComment();
        } else {
            // 不乐观情况，方法被重载了，需要遍历对比参数列表的全限定名
            for (JavaMethod method : nameMatchedMethods) {
                Parameter[] parameters = handlerMethod.getMethod().getParameters();
                List<JavaType> parameterTypes = method.getParameterTypes();
                if (parameters.length == parameterTypes.size()) {
                    for (int i = 0; i < parameters.length; i++) {
                        if (parameters[i].getType().getName().equals(parameterTypes.get(i).getFullyQualifiedName())) {
                            summary = method.getComment();
                        }
                    }
                }
            }
        }
        context.operationBuilder().summary(summary);
    }

    @SuppressWarnings("unchecked")
    private <T> T getFieldValue(Class<?> targetType, Object target, String fieldName) {
        Field field = Objects.requireNonNull(ReflectionUtils.findField(targetType, fieldName));
        field.setAccessible(true);
        return (T) ReflectionUtils.getField(field, target);
    }

}
