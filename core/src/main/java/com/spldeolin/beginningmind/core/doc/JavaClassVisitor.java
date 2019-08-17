package com.spldeolin.beginningmind.core.doc;

import org.apache.commons.lang3.StringUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;

/**
 * @author Deolin 2019-08-16
 */
public class JavaClassVisitor {

    public static boolean isController(JavaClass javaClass) {
        for (JavaAnnotation anno : javaClass.getAnnotations()) {
            String annoName = anno.getType().getFullyQualifiedName();
            if (StringUtils.equalsAny(annoName, "org.springframework.stereotype.Controller",
                    "org.springframework.web.bind.annotation.RestController")) {
                return true;
            }
        }
        return false;
    }

}
