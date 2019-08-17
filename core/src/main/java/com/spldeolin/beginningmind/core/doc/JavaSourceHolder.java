package com.spldeolin.beginningmind.core.doc;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaSource;
import com.thoughtworks.qdox.model.JavaType;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2019-08-17
 */
@Component
@Log4j2
public class JavaSourceHolder {

    private final Map<String, JavaClass> classSources = Maps.newHashMap();

    private final Map<String, JavaClass> controllerSources = Maps.newHashMap();

    /**
     * 方法的全限定名：方法每个参数的类型的全限定名：方法注释
     */
    private final Table<String, List<String>, String> methodCommentMap = HashBasedTable.create();

    private final JavaProjectBuilder builder = new JavaProjectBuilder();

    private static final String projectPath = "/Users/deolin/Documents/project-repo/beginning-mind";

    @PostConstruct
    public void init() {
        Iterator<File> javaFiles = FileUtils.iterateFiles(new File(projectPath), new String[]{"java"}, true);
        while (javaFiles.hasNext()) {
            try {
                File javaFile = javaFiles.next();
                JavaSource src = builder.addSource(javaFile);
                for (JavaClass javaClass : src.getClasses()) {
                    String fqName = javaClass.getFullyQualifiedName();
                    classSources.put(fqName, javaClass);
                    if (JavaClassVisitor.isController(javaClass)) {
                        controllerSources.put(fqName, javaClass);
                    }

                    // extract method comment
                    for (JavaMethod javaMethod : javaClass.getMethods()) {
                        String methodFqName = fqName + "." + javaMethod.getName();
                        List<String> parameterFqNames = Lists.newArrayList();
                        for (JavaType javaType : javaMethod.getParameterTypes()) {
                            parameterFqNames.add(javaType.getFullyQualifiedName());
                        }
                        String comment = "";
                        if (javaMethod.getComment() != null) {
                            comment = javaMethod.getComment();
                        }
                        methodCommentMap.put(methodFqName, parameterFqNames, comment);
                    }
                }
            } catch (IOException e) {
                log.info(e);
            }
        }
    }

    public JavaClass getSource(String fullyQualifiedName) {
        return classSources.get(fullyQualifiedName);
    }

    public String getMethodComment(String methodFullyQualifiedName, List<String> parameterTypeFullQulifiedNames) {
        return methodCommentMap.get(methodFullyQualifiedName, parameterTypeFullQulifiedNames);
    }

}
