package com.spldeolin.beginningmind.core.doc.visitor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/08/24
 */
@Log4j2
public class ClassVisitor {

    public static boolean isAvailableController(Class clazz) {
        return isController(clazz) && hasRequestMethod(clazz);
    }

    public static boolean isController(Class clazz) {
        boolean isController = false;
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation instanceof Controller || annotation instanceof RestController) {
                isController = true;
                break;
            }
        }
        return isController;
    }

    public static boolean hasRequestMethod(Class clazz) {
        boolean hasRequestMethod = false;
        for (Method method : clazz.getDeclaredMethods()) {
            if (MethodVisitor.isRequestMethod(method)) {
                hasRequestMethod = true;
                break;
            }
        }
        return hasRequestMethod;
    }

    public static List<Method> listRequestMethods(Class clazz) {
        List<Method> result = Lists.newArrayList();
        for (Method method : clazz.getDeclaredMethods()) {
            if (MethodVisitor.isRequestMethod(method)) {
                result.add(method);
            }
        }
        return result;
    }

    public static String getControllerUrl(Class clazz) {
        RequestMapping requestMapping = getAnnotation(clazz, RequestMapping.class);
        if (requestMapping == null) {
            return "";
        }
        String[] values = requestMapping.value();
        if (values.length == 0) {
            return "";
        }
        if (values.length > 1) {
            log.warn("{} 指定了多个RequestMapping，只选取第一个");
        }
        return values[0];
    }

    public static String getClassName(Class clazz) {
        return clazz.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T getAnnotation(Class clazz, Class<T> annotationType) {
        Annotation annotation = clazz.getAnnotation(annotationType);
        return (T) annotation;
    }


}
