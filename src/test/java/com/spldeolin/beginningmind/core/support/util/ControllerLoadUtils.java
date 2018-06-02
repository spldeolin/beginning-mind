package com.spldeolin.beginningmind.core.support.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.support.dto.ControllerDefinition;
import lombok.experimental.UtilityClass;

/**
 * @author Deolin 2018/06/02
 */
@UtilityClass
public class ControllerLoadUtils {

    public static List<ControllerDefinition> loadControllers(String packagePath, String packageReference,
            boolean recursive) {
        List<Class> classes = ClassLoadUtils.loadClassesInFile(packagePath, packageReference, true);
        List<ControllerDefinition> result = new ArrayList<>();
        for (Class clazz : classes) {
            if (isContoller(clazz)) {
                List<Method> requestMethods = new ArrayList<>();
                for (Method method : clazz.getDeclaredMethods()) {
                    if (isRequestMethod(method)) {
                        requestMethods.add(method);
                    }
                }
                if (requestMethods.size() > 0) {
                    ControllerDefinition controllerDefinition = new ControllerDefinition();
                    controllerDefinition.setController(clazz);
                    controllerDefinition.setRequestMethods(requestMethods);
                    result.add(controllerDefinition);
                }
            }
        }
        return result;
    }

    /**
     * 判断Class对象是否有控制器声明
     */
    private static boolean isContoller(Class clazz) {
        boolean isController = false;
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation instanceof RestController || annotation instanceof Controller) {
                isController = true;
                break;
            }
        }
        return isController;
    }

    /**
     * 判断Method对象是否有请求方法声明
     */
    private static boolean isRequestMethod(Method method) {
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

}
