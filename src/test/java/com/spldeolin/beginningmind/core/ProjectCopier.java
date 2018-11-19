package com.spldeolin.beginningmind.core;

import java.io.File;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.generator.util.StringCaseUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/16
 */
@Log4j2
public class ProjectCopier {

    private static String groupId = "com.spldeolin";

    private static String projectName = "beginning-mind"; // 横线分割

    private static String projectPath = "C:\\java-development\\projects-repo\\deolin-projects\\beginning-mind-2"; // 无需\\结尾

    private static String basePackage = "com.spldeolin.beginningmind.core";

    private static String sep = File.separator;

    public static void main(String[] args) {
        String thisPath = System.getProperty("user.dir") + sep;
        String projectNameLowerCamel = StringCaseUtils.snakeToLowerCamel(projectName);
        String projectNameUpperCamel = StringCaseUtils.snakeToUpperCamel(projectName);

        // src文件夹下的文件
        for (File srcFile : Lists.newArrayList(FileUtils.iterateFiles(new File(thisPath + sep + "src"), null, true))) {
            String content = readFile(srcFile);
            content = content.replace("com.spldeolin.beginningmind.core", basePackage);
            content = content.replace("beginning-mind", projectName);
            content = content.replace("beginningMind", projectNameLowerCamel);
            File copiedFile = new File(srcFile.getPath().replace(thisPath, projectPath + sep)
                    .replace("com.spldeolin.beginningmind.core".replace(".", sep), basePackage.replace(".", sep)));

            writeFile(copiedFile, content);
        }

        // pom.xml
        File pomFile = new File(thisPath + "pom.xml");
        String content = readFile(pomFile);
        content = content.replace("com.spldeolin", groupId);
        content = content.replace("beginning-mind", projectName);
        writeFile(new File(pomFile.getPath().replace(thisPath, projectPath + sep)), content);

        log.info("SUCCESS");
    }

    @SneakyThrows
    private static String readFile(File file) {
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    private static void writeFile(File file, String content) {
        FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
    }

}
