package com.spldeolin.beginningmind.core.support;

import static com.spldeolin.beginningmind.core.constant.Abbreviation.sep;
import static com.spldeolin.beginningmind.core.constant.Abbreviation.utf8;

import java.io.File;
import org.apache.commons.io.FileUtils;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/08/07
 */
@Log4j2
public class ForkBeginningMind {

    static String groupId = "com.womenhz";

    static String artifactId = "xxx";

    static String forkedProjectName = "xxx";

    static String forkedProjectPath = "C:\\java-development\\projects-repo\\women-projects\\xxx";

    static String forkedBasePackage = "com.womenhz.xxx.core";

    @SneakyThrows
    public static void main(String[] args) {
        String thisPath = System.getProperty("user.dir") + sep;
        File beginningMindDirectory = new File(thisPath);

        // src
        for (File srcFile : Lists.newArrayList(FileUtils.iterateFiles(beginningMindDirectory, null, true))) {
            if (srcFile.getPath().startsWith(thisPath + "src")) {
                String content = FileUtils.readFileToString(srcFile, utf8);
                content = content.replace("package com.spldeolin.beginningmind.core", "package " + forkedBasePackage);
                content = content.replace("import static com.spldeolin.beginningmind.core",
                        "import static " + forkedBasePackage);
                content = content.replace("import com.spldeolin.beginningmind.core", "import " + forkedBasePackage);
                content = content.replace("beginningmind", forkedProjectName);

                File forkedFile = new File(srcFile.getPath().replace(thisPath, forkedProjectPath + sep)
                        .replace("com.spldeolin.beginningmind.core".replace(".", sep),
                                forkedBasePackage.replace(".", sep)));
                FileUtils.writeStringToFile(forkedFile, content, utf8);
            }
        }

        // pom
        File pomFile = new File(thisPath + "pom.xml");
        String content = FileUtils.readFileToString(pomFile, utf8);
        content = content.replace("\n    <description>初心</description>", "");
        content = content.replace("com.spldeolin", groupId);
        content = content.replace("beginning-mind", artifactId);

        File forkedFile = new File(pomFile.getPath().replace(thisPath, forkedProjectPath + sep)
                .replace("com.spldeolin.beginningmind.core".replace(".", sep),
                        forkedBasePackage.replace(".", sep)));
        FileUtils.writeStringToFile(forkedFile, content, utf8);

        log.info("SUCCESS");
    }

}
