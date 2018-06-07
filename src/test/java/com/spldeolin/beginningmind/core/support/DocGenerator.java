package com.spldeolin.beginningmind.core.support;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.FileUtils;
import com.spldeolin.beginningmind.core.support.dto.ControllerDefinition;
import com.spldeolin.beginningmind.core.support.dto.MarkdownDocFTL;
import com.spldeolin.beginningmind.core.support.util.ControllerLoadUtils;
import com.spldeolin.beginningmind.core.support.util.FreeMarkerUtil;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/06/02
 */
@UtilityClass
@Log4j2
public class DocGenerator {

    @SneakyThrows
    public static void main(String[] args) {
        String packageReference = "com.spldeolin.beginningmind.core.controller";
        String packagePath = "C:\\java-development\\projects-repo\\deolin-projects\\beginning-mind\\src\\main\\java\\" +
                "com\\spldeolin\\beginningmind\\core\\controller";
        // 获取指定包下所有的**有效**控制器
        List<ControllerDefinition> controllers = ControllerLoadUtils.loadControllers(packagePath,
                packageReference, true);
        // 遍历每个请求方法
        for (ControllerDefinition definition : controllers) {
            Class controller = definition.getController();
            for (Method requestMethod : definition.getRequestMethods()) {
                // 组装数据
                MarkdownDocFTL template = new MarkdownDocFTL();
                template.fromDocClassDTO(DocClassResolver.resolve(controller));
                template.fromClassRequestMappingDTO(ClassRequestMappingResolver.resolve(controller));
                RequestMethodAnalysiser.analysis(requestMethod, template);
                // 渲染Free Marker，生成Markdown文件
                template.setParamShow(false);
                log.info(template);
                String content = FreeMarkerUtil.format("markdown-doc.ftl", template);
                FileUtils.writeStringToFile(
                        new File("C:\\mddoc\\" + template.getDirectoryName() + "\\" + template.getCommonDesc() + ".md"),
                        content,
                        StandardCharsets.UTF_8);
            }
        }


    }


}
