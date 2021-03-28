package com.spldeolin.beginningmind;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import com.spldeolin.allison1875.base.ast.MavenPathResolver;
import lombok.extern.slf4j.Slf4j;

/**
 * 分裂
 *
 * @author Deolin 2020-11-27
 */
@Slf4j
public class Division {

    public static void main(String[] args) {
        final String moduleName = "target-project";
        final String moduleDescription = "目标项目";

        final File stemDirectory = MavenPathResolver.findMavenProject(Division.class).toFile();

        String destPath = stemDirectory.getPath().replace("beginning-mind", moduleName);
        if (new File(destPath).exists()) {
            log.error("project [{}] is already exist. [{}]", moduleName, destPath);
            return;
        }

        FileUtils.iterateFiles(stemDirectory, null, true).forEachRemaining(file -> {
            String fileName = file.getName();
            String fileExtension = FilenameUtils.getExtension(fileName);
            if ("Division.java".equals(fileName)) {
                log.info("ignore [" + file.getPath() + "]");
                return;
            }
            if (".DS_Store".equals(fileName) || "class".equals(fileExtension) || "iml".equals(fileExtension)) {
                return;
            }
            if (file.getPath().contains(File.separator + ".idea" + File.separator) || file.getPath()
                    .contains(File.separator + "target" + File.separator) || file.getPath()
                    .contains(File.separator + ".git" + File.separator) || file.getPath()
                    .contains(File.separator + "logs" + File.separator)) {
                return;
            }

            try {
                String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                content = content.replace("beginning-mind", moduleName);
                content = content.replace("beginningmind", moduleName.replace("-", ""));
                content = content.replace("beginning_mind", moduleName.replace("-", "_"));
                if ("README.md".equals(fileName)) {
                    content = String.format("# *%s*\n%s", moduleName, moduleDescription);
                }

                String path = file.getPath();
                path = path.replace("beginning-mind", moduleName);
                path = path.replace("beginningmind", moduleName.replace("-", ""));
                FileUtils.writeStringToFile(new File(path), content, StandardCharsets.UTF_8);
                log.info("create file [{}]", file);
            } catch (IOException e) {
                log.error("IO error", e);
            }
        });
        log.info("Division fully completed. [{}]", destPath);
    }

}