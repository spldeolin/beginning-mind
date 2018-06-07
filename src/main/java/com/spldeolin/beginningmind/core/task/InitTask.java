package com.spldeolin.beginningmind.core.task;

import java.net.InetAddress;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.service.ImageService;
import com.spldeolin.beginningmind.core.service.SignService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * 项目刚启动执行一次的任务汇总
 *
 * @author Deolin 2018/05/31
 */
@Component
@Log4j2
public class InitTask {

    @Value("${server.context-path}")
    private String serverContextPath;

    @Autowired
    private CoreProperties properties;

    /**
     * 将配置中出现的所有localhost替换为本地IP
     */
    @PostConstruct
    @SneakyThrows
    public void localhostToLocalIp() {
        String address = properties.getAddress();
        address = address.replace("localhost", InetAddress.getLocalHost().getHostAddress());
        properties.setAddress(address);
    }

    /**
     * 在file.mapping前追加content-path
     */
    @PostConstruct
    public void mappingAppendContextPath() {
        if (StringUtils.isNotBlank(serverContextPath) && !serverContextPath.equals("/")) {
            CoreProperties.File file = properties.getFile();
            String mapping = serverContextPath + file.getMapping();
            log.info("由于指定了context-path为" + serverContextPath + "，故file.mapping变更为" + mapping);
            file.setMapping(mapping);
        }
    }

    /**
     * 确保file.mapping以 / 开头和结尾，确保file.location以 / 结尾
     */
    @PostConstruct
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

    /**
     * 确保本地文件夹存在
     */
    @PostConstruct
    @SneakyThrows
    public void ensureDirectoryExist() {
        CoreProperties.File file = properties.getFile();
        FileUtils.mkdir(new java.io.File(file.getLocation() + ImageService.IMAGE_DIRECTORY), true);
        FileUtils.mkdir(new java.io.File(file.getLocation() + SignService.CAPTCHA_DIRECTORY), true);
    }

}
