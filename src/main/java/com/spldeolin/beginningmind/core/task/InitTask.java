package com.spldeolin.beginningmind.core.task;

import java.net.InetAddress;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.service.ImageService;
import com.spldeolin.beginningmind.core.service.SignService;
import com.spldeolin.beginningmind.core.task.holder.RequestMethodDefinitionsHolder;
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
    private CoreProperties properties;

    @Autowired
    private RequestMethodDefinitionsHolder requestMethodDefinitionsHolder;

    @Override
    public void run(String... args) {
        // 将配置中出现的所有localhost替换为本地IP
        localhostToLocalIp();
        // 在file.mapping前追加content-path
        mappingAppendContextPath();
        // 确保file.mapping以 / 开头和结尾，确保file.location以 / 结尾
        validateProperties();
        // 确保本地目录存在
        ensureDirectoryExist();
        // 初始化mapping一览
        requestMethodDefinitionsHolder.init();
        log.info("启动成功");
    }

    @SneakyThrows
    public void localhostToLocalIp() {
        String address = properties.getAddress();
        address = address.replace("localhost", InetAddress.getLocalHost().getHostAddress());
        properties.setAddress(address);
    }

    public void mappingAppendContextPath() {
        if (StringUtils.isNotBlank(serverContextPath) && !serverContextPath.equals("/")) {
            CoreProperties.File file = properties.getFile();
            String mapping = serverContextPath + file.getMapping();
            log.info("由于指定了context-path为" + serverContextPath + "，故file.mapping变更为" + mapping);
            file.setMapping(mapping);
        }
    }

    public void validateProperties() {
        CoreProperties.File file = properties.getFile();
        if (!file.getMapping().endsWith("/")) {
            throw new IllegalArgumentException("core.file.mapping必须以 / 结尾");
        }
        if (!file.getLocation().endsWith("/")) {
            throw new IllegalArgumentException("core.file.location必须以 / 结尾");
        }
        if (!file.getMapping().endsWith("/")) {
            throw new IllegalArgumentException("core.file.mapping必须以 / 开头");
        }
    }

    @SneakyThrows
    public void ensureDirectoryExist() {
        CoreProperties.File file = properties.getFile();
        FileUtils.mkdir(new java.io.File(file.getLocation() + ImageService.IMAGE_DIRECTORY), true);
        FileUtils.mkdir(new java.io.File(file.getLocation() + SignService.CAPTCHA_DIRECTORY), true);
    }
}
