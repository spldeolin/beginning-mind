package com.spldeolin.beginningmind.core.config;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.collect.Maps;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2019-09-20
 */
@Configuration
@Log4j2
public class JavaSourceConfig {

    public static final String JAVA_CLASSES = "javaClasses";

    private static final String PROJECT_PATH = "/Users/deolin/Documents/project-repo/beginning-mind";

    @Bean(name = JAVA_CLASSES)
    public Map<String, JavaClass> loadJavas() {
        Map<String, JavaClass> result = Maps.newHashMap();
        JavaProjectBuilder builder = new JavaProjectBuilder();

        Iterator<File> javaFiles = FileUtils.iterateFiles(new File(PROJECT_PATH), new String[]{"java"}, true);
        while (javaFiles.hasNext()) {
            File javaFile = javaFiles.next();
            try {
                JavaSource src = builder.addSource(javaFile);
                src.getClasses().forEach(javaClass -> {
                    String qualifiedName = javaClass.getFullyQualifiedName();
                    result.put(qualifiedName, javaClass);
                    log.debug("Qdox解析[{}]完毕", qualifiedName);
                });
            } catch (Exception e) {
                log.warn("Qdox解析文件[{}]失败", javaFile, e);
            }
        }
        return result;
    }

}
