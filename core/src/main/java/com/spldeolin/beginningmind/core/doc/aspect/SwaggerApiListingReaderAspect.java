package com.spldeolin.beginningmind.core.doc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.doc.JavaSourceHolder;
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
    private JavaSourceHolder srcHolder;

    @Pointcut("execution(* springfox.documentation.swagger.web.SwaggerApiListingReader.apply(springfox.documentation"
            + ".spi.service.contexts.ApiListingContext))")
    public void apply() {
    }

    @Around("apply()")
    public void around(ProceedingJoinPoint point) throws Throwable {
        ApiListingContext api = (ApiListingContext) point.getArgs()[0];

        point.proceed(point.getArgs());

        api.getResourceGroup().getControllerClass().toJavaUtil().ifPresent(one -> {
            JavaClass javaClass = srcHolder.getJava(one.getName());
            api.apiListingBuilder().description(javaClass.getComment());
        });
    }

}
