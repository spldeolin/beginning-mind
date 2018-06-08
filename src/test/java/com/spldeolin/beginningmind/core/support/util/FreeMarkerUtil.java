package com.spldeolin.beginningmind.core.support.util;

import static com.spldeolin.beginningmind.core.constant.Abbreviation.sep;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FreeMarkerUtil {

    /**
     * 根据ftl文件和数据，生成字符内容。
     *
     * @param ftlFile ftl文件
     * @param datas 数据
     * @return 格式化后的内容
     */
    public static String format(String ftlFile, Object datas) {
        if (!ftlFile.endsWith(".ftl")) {
            ftlFile += ".ftl";
        }
        Version version = new Version("2.3.23");
        Configuration cfg = new Configuration(version);
        try {
            String folderPath = System.getProperty("user.dir") + sep + "src" + sep + "main" + sep + "resources" + sep
                    + "freemarker-template" + sep;
            cfg.setDirectoryForTemplateLoading(new File(folderPath));
            Template template = cfg.getTemplate(ftlFile, "utf-8");
            StringWriter out = new StringWriter();
            template.process(datas, out);
            out.flush();
            out.close();
            return out.getBuffer().toString();
        } catch (IOException | TemplateException e) {
            log.error("checked", e);
            throw new RuntimeException();
        }
    }

}
