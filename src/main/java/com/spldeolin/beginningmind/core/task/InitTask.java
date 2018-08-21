package com.spldeolin.beginningmind.core.task;

import java.net.InetAddress;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.constant.DirectoryName;
import com.spldeolin.beginningmind.core.holder.RequestMethodDefinitionsHolder;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * 项目刚启动执行一次的任务汇总
 *
 * @author Deolin 2018/05/31
 */
@Component
@Log4j2
public class InitTask implements CommandLineRunner {

    @Value("${server.context-path}")
    private String serverContextPath;

    @Autowired
    private CoreProperties coreProperties;

    @Autowired
    private RequestMethodDefinitionsHolder requestMethodDefinitionsHolder;

    @Override
    public void run(String... args) {
        // 将配置中出现的所有localhost替换为本地IP
        localhostToLocalIp();

        // 在file.mapping前追加content-path
        mappingAppendContextPath();

        // 确保file.mapping以 / 开头和结尾，确保file.location以 / 结尾
        ensureMappingAndLocationStartEndWithVirgule();

        // 确保本地目录存在
        ensureDirectoryExist();

        // 读取RequestMethod信息，并载入内存
        requestMethodDefinitionsHolder.init();
    }

    @SneakyThrows
    public void localhostToLocalIp() {
        String address = coreProperties.getAddress();
        address = address.replace("localhost", InetAddress.getLocalHost().getHostAddress());
        coreProperties.setAddress(address);
    }

    public void mappingAppendContextPath() {
        if (StringUtils.isNotBlank(serverContextPath) && !serverContextPath.equals("/")) {
            CoreProperties.File file = coreProperties.getFile();
            String mapping = serverContextPath + file.getMapping();
            log.info("由于指定了context-path为" + serverContextPath + "，故file.mapping变更为" + mapping);
            file.setMapping(mapping);
        }
    }

    public void ensureMappingAndLocationStartEndWithVirgule() {
        CoreProperties.File file = coreProperties.getFile();
        String mapping = file.getMapping();
        String location = file.getLocation();

        if (!mapping.endsWith("/")) {
            file.setMapping(mapping + "/");
        }
        if (!mapping.startsWith("/")) {
            file.setMapping("/" + mapping);
        }
        if (!file.getLocation().endsWith("/")) {
            file.setMapping(location + "/");
        }
    }

    @SneakyThrows
    public void ensureDirectoryExist() {
        CoreProperties.File file = coreProperties.getFile();
        FileUtils.mkdir(new java.io.File(file.getLocation() + DirectoryName.IMAGE_DIRECTORY), true);
        FileUtils.mkdir(new java.io.File(file.getLocation() + DirectoryName.CAPTCHA_DIRECTORY), true);
    }

}
