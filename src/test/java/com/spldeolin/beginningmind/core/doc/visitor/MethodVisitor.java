package com.spldeolin.beginningmind.core.doc.visitor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/08/24
 */
@Log4j2
public class MethodVisitor {

    public static boolean isRequestMethod(Method method) {
        boolean isRequestMethod = false;
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof RequestMapping || annotation instanceof GetMapping ||
                    annotation instanceof PostMapping || annotation instanceof PutMapping ||
                    annotation instanceof DeleteMapping) {
                isRequestMethod = true;
                break;
            }
        }
        return isRequestMethod;
    }

    public static String getControllerUrl(Method method) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof RequestMapping) {
                return ((RequestMapping) annotation).value()[0];
            } else if (annotation instanceof GetMapping) {
                return ((GetMapping) annotation).value()[0];
            } else if (annotation instanceof PostMapping) {
                return ((PostMapping) annotation).value()[0];
            } else if (annotation instanceof PutMapping) {
                return ((PutMapping) annotation).value()[0];
            } else if (annotation instanceof DeleteMapping) {
                return ((DeleteMapping) annotation).value()[0];
            }
        }
        throw new RuntimeException("impossbile");
    }

    public static String getMethodName(Method method) {
        return method.getName();
    }

    public static List<Parameter> listParameters(Method method) {
        return Lists.newArrayList(method.getParameters());
    }

}
