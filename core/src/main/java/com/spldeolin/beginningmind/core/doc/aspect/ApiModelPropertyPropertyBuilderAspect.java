package com.spldeolin.beginningmind.core.doc.aspect;

import java.lang.reflect.Field;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.doc.JavaSourceHolder;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.schema.ApiModelPropertyPropertyBuilder;

/**
 * 增强Springfox提取@ApiModelProperty的功能，重写为利用qdox提取Javadoc
 *
 * @author Deolin 2019-08-18
 * @see ApiModelPropertyPropertyBuilder#apply(springfox.documentation.spi.schema.contexts.ModelPropertyContext)
 */
@Aspect
@Component
public class ApiModelPropertyPropertyBuilderAspect {

    @Autowired
    private JavaSourceHolder srcHolder;

    @Pointcut("execution(* springfox.documentation.swagger.schema.ApiModelPropertyPropertyBuilder.apply"
            + "(springfox.documentation.spi.schema.contexts.ModelPropertyContext))")
    public void apply() {
    }

    @Around("apply()")
    public void around(ProceedingJoinPoint point) throws Throwable {
        ModelPropertyContext context = (ModelPropertyContext) point.getArgs()[0];

        point.proceed(point.getArgs());

        context.getBeanPropertyDefinition().toJavaUtil().ifPresent(one -> {
            Field field = one.getField().getAnnotated();
            String pojoFqName = field.getDeclaringClass().getName();
            String fqName = pojoFqName + "." + field.getName();
            context.getBuilder().description(srcHolder.getFieldComment(fqName));

            // 保持原有顺序
            context.getBuilder().position(srcHolder.getFiledIndexOfPojo(pojoFqName, field.getName()));

            // @NotNull、@NotEmpty、@NotBlank全部当作required
            if (field.getType().equals(String.class)) {
                context.getBuilder().required(srcHolder.isFieldNotNullOrNotEmptyOrNotBlank(fqName));
            }
        });

    }

}
