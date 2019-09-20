package com.spldeolin.beginningmind.core.doc.aspect;

import java.lang.reflect.Field;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.config.JavaSourceConfig;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
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
    @Qualifier(JavaSourceConfig.JAVA_CLASSES)
    private Map<String, JavaClass> javaClasses;

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
            String pojoQualifiedName = field.getDeclaringClass().getName();
            JavaClass pojo = javaClasses.get(pojoQualifiedName);
            JavaField javaField = pojo.getFieldByName(field.getName());
            // 替换注释
            context.getBuilder().description(javaField.getComment());

            // 保持原有顺序
            for (int i = 0; i < pojo.getFields().size(); i++) {
                if (pojo.getFields().get(i).getName().equals(field.getName())) {
                    context.getBuilder().position(i);
                }
            }

            // @NotNull、@NotEmpty、@NotBlank全部当作required
            if (field.getType().equals(String.class) && javaField.getAnnotations().stream().anyMatch(anno -> StringUtils
                    .equalsAny(anno.getType().getFullyQualifiedName(), "javax.validation.constraints.NotBlank",
                            "javax.validation.constraints.NotEmpty", "javax.validation.constraints.NotNull"))) {
                context.getBuilder().required(true);
            }
        });

    }

}
