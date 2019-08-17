package com.spldeolin.beginningmind.core.doc;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import com.google.common.collect.Maps;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2019-08-17
 */
@Component
@Log4j2
public class JavaSourceHolder {

    private final Map<String, JavaClass> classSources = Maps.newHashMap();

    private final Map<String, JavaClass> controllerSources = Maps.newHashMap();


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
                }
            } catch (IOException e) {
                log.info(e);
            }
        }
    }

    public JavaClass getSource(String fullyQualifiedName) {
        return classSources.get(fullyQualifiedName);
    }

}
