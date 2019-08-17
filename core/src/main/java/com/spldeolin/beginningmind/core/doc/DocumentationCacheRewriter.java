package com.spldeolin.beginningmind.core.doc;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import lombok.extern.log4j.Log4j2;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiListing;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.ControllerNamingUtils;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.paths.Paths;

/**
 * @author Deolin 2019-08-16
 */
//@Component
@Log4j2
public class DocumentationCacheRewriter implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private DocumentationCache documentationCache;

    private final JavaProjectBuilder builder = new JavaProjectBuilder();

    private static final String projectPath = "/Users/deolin/Documents/project-repo/beginning-mind";

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        // k是groupName即类名，需要考虑同类名、不同包、不同BeanName、不同类级RequestMapping的情况，所以选用Table。
        // 泛型分别指的是 类名：类级RequestMapping：JavaClass
        Map<String, JavaClass> javaClasses = Maps.newHashMap();
        Iterator<File> javaFiles = FileUtils.iterateFiles(new File(projectPath), new String[]{"java"}, true);
        while (javaFiles.hasNext()) {
            try {
                File javaFile = javaFiles.next();
                JavaSource src = builder.addSource(javaFile);
                for (JavaClass javaClass : src.getClasses()) {
                    if (javaClass.isPublic()) {
                        String groupName = controllerNameAsGroup(javaClass);
                        javaClasses.put(groupName, javaClass);
                        break; // 一个源文件只会有一个public class，找到后即可break
                    }
                }
            } catch (IOException e) {
                log.info(e);
            }
        }


        for (Documentation doc : documentationCache.all().values()) {
            Multimap<String, ApiListing> rewrited = HashMultimap.create();
            for (Entry<String, ApiListing> apiEachGroup : doc.getApiListings().entries()) {
                String groupName = apiEachGroup.getKey();
                for (ApiDescription api : apiEachGroup.getValue().getApis()) {
//                    JavaClass javaClass = javaClasses.get(groupName, api.getPath());
//                    String  comment = JavaClassVisitor
                }

                System.out.println(1);
            }
        }
    }

    /**
     * @see ControllerNamingUtils#controllerNameAsGroup(org.springframework.web.method.HandlerMethod)
     */
    private String controllerNameAsGroup(JavaClass controllerName) {
        return Paths.splitCamelCase(controllerName.getName(), "-").replace("/", "").toLowerCase();
    }

}
