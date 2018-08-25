package com.spldeolin.beginningmind.core.doc;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.core.doc.loader.ProjectClassLoader;
import com.spldeolin.beginningmind.core.doc.loader.ProjectJavaDocLoader;
import com.spldeolin.beginningmind.core.doc.visitor.ClassDocVisitor;
import com.spldeolin.beginningmind.core.doc.visitor.ClassVisitor;
import com.spldeolin.beginningmind.core.doc.visitor.MethodDocVisitor;
import com.spldeolin.beginningmind.core.doc.visitor.MethodVisitor;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/08/20
 */
@Log4j2
public class DocGenerator {

    public static final String BASE_PACKAGE = "com.spldeolin.beginningmind.core";

    @SneakyThrows
    public static void main(String[] args) {
        Map<Class, ClassDoc> classMap = mapClassAndClassDoc(ProjectClassLoader.listClassesRecursively(BASE_PACKAGE),
                ProjectJavaDocLoader.listClassDocsRecursively(BASE_PACKAGE));

        for (Entry<Class, ClassDoc> entry : filterControllers(classMap).entrySet()) {
            Class clazz = entry.getKey();
            ClassDoc classDoc = entry.getValue();

            String classComment = ClassDocVisitor.getComment(classDoc);
            List<String> classAuthors = ClassDocVisitor.listAuthors(classDoc);
            String controllerUrl = ClassVisitor.getControllerUrl(clazz);
            String className = ClassVisitor.getClassName(clazz);

            for (Method method : ClassVisitor.listRequestMethods(clazz)) {
                MethodDoc methodDoc = ClassDocVisitor.getMethod(classDoc, method);

                String methodComment = MethodDocVisitor.getComment(methodDoc);
                List<String> methodAuthors = MethodDocVisitor.listAuthors(methodDoc);
                List<String> actualAuthors = judgeOutActualAuthor(classAuthors, methodAuthors);
                String requestMethodUrl = MethodVisitor.getControllerUrl(method);
                String url = appendUrl(controllerUrl, requestMethodUrl);
                String methodName = MethodVisitor.getMethodName(method);

                // 方法参数
                for (Parameter parameter : MethodVisitor.listParameters(method)) {

                }

                // 返回值

            }
        }
    }

    private static Map<Class, ClassDoc> mapClassAndClassDoc(List<Class> classes, List<ClassDoc> classDocs) {
        log.info("classes 数量 {}", classes.size());
        log.info("classDocs 数量 {}", classDocs.size());

        Map<Class, ClassDoc> map = Maps.newHashMap();

        for (Class clazz : classes) {
            String qualifiedName = clazz.getName();
            for (ClassDoc classDoc : classDocs) {
                if (classDoc.qualifiedName().equals(qualifiedName)) {
                    map.put(clazz, classDoc);
                    break;
                }
            }
        }

        List<String> allClasses = classes.stream().map(Class::getName).collect(Collectors.toList());
        List<String> matchedClasses = map.keySet().stream().map(Class::getName).collect(Collectors.toList());
        allClasses.removeAll(matchedClasses);
        allClasses.forEach(c -> log.info("未匹配到classDoc的class {}", c));

        List<String> allClassDocs = classDocs.stream().map(ClassDoc::qualifiedName).collect(Collectors.toList());
        List<String> matchedClassDocs = map.values().stream().map(ClassDoc::qualifiedName).collect(Collectors.toList());
        allClassDocs.removeAll(matchedClassDocs);
        allClassDocs.forEach(c -> log.info("未匹配到class的classDoc {}", c));

        return map;
    }

    private static Map<Class, ClassDoc> filterControllers(Map<Class, ClassDoc> classMap) {
        Map<Class, ClassDoc> controllerMap = Maps.newHashMap();
        for (Entry<Class, ClassDoc> entry : classMap.entrySet()) {
            Class clazz = entry.getKey();
            if (ClassVisitor.isAvailableController(clazz)) {
                controllerMap.put(clazz, entry.getValue());
            }
        }
        return controllerMap;
    }

    private static List<String> judgeOutActualAuthor(List<String> classAuthors, List<String> methodAuthors) {
        if (methodAuthors.size() > 0) {
            return methodAuthors;
        } else {
            return classAuthors;
        }
    }

    private static String appendUrl(String controllerUrl, String requestMethodUrl) {
        if (controllerUrl.equals("/")) {
            controllerUrl = "";
        }
        if (requestMethodUrl.equals("/")) {
            requestMethodUrl = "";
        }
        String result = controllerUrl + requestMethodUrl;
        if (result.length() == 0) {
            result = "/";
        }
        return result;
    }

}
