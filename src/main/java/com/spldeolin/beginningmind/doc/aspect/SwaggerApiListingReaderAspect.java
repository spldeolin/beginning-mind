package com.spldeolin.beginningmind.doc.aspect;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import com.spldeolin.beginningmind.doc.config.JavaSourceConfig;
import com.thoughtworks.qdox.model.JavaClass;
import springfox.documentation.spi.service.contexts.ApiListingContext;
import springfox.documentation.swagger.web.SwaggerApiListingReader;

/**
 * 增强Springfox提取@Api#description的功能，重写为利用qdox提取Javadoc
 *
 * @author Deolin 2019-08-17
 * @see SwaggerApiListingReader#apply(ApiListingContext)
 */
@Aspect
@Component
public class SwaggerApiListingReaderAspect {

    @Autowired
    @Qualifier(JavaSourceConfig.JAVA_CLASSES)
    private Map<String, JavaClass> javaClasses;

    @Pointcut("execution(* springfox.documentation.swagger.web.SwaggerApiListingReader.apply"
            + "(springfox.documentation.spi.service.contexts.ApiListingContext))")
    public void apply() {
    }

    @Around("apply()")
    public void around(ProceedingJoinPoint point) throws Throwable {
        ApiListingContext api = (ApiListingContext) point.getArgs()[0];

        point.proceed(point.getArgs());

        api.getResourceGroup().getControllerClass().toJavaUtil().ifPresent(one -> {
            JavaClass javaClass = javaClasses.get(one.getName());
            if (javaClass == null) {
                return;
            }
            api.apiListingBuilder().description(" ");
            String comment = javaClass.getComment();
            if (StringUtils.isBlank(comment)) {
                comment = javaClass.getName();
                api.apiListingBuilder().description(comment);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T> T getFieldValue(Class<?> targetType, Object target, String fieldName) {
        Field field = Objects.requireNonNull(ReflectionUtils.findField(targetType, fieldName));
        field.setAccessible(true);
        return (T) ReflectionUtils.getField(field, target);
    }

}
