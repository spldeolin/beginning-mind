package com.spldeolin.beginningmind.core.task;

import java.net.InetAddress;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.CoreProperties.FileProp;
import com.spldeolin.beginningmind.core.constant.DirectoryName;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/16
 */
@Component
@Log4j2
public class InitTask implements CommandLineRunner {

    @Value("${server.contextPath}")
    private String serverContextPath;

    @Autowired
    private CoreProperties coreProperties;

    @Override
    public void run(String... args) {
        // 将配置中出现的所有localhost替换为本地IP
        localhostToLocalIp();

        // 在file.mapping前追加content-path
        appendContextPathBeforeFileMapping();

        // 确保本地目录存在
        ensureDirectoryExist();
    }

    @SneakyThrows
    public void localhostToLocalIp() {
        String address = coreProperties.getAddress();
        address = address.replace("localhost", InetAddress.getLocalHost().getHostAddress());
        coreProperties.setAddress(address);
    }

    public void appendContextPathBeforeFileMapping() {
        if (StringUtils.isNotBlank(serverContextPath) && !serverContextPath.equals("/")) {
            FileProp file = coreProperties.getFile();
            String mapping = serverContextPath + file.getMapping();
            log.info("由于指定了context-path为" + serverContextPath + "，故file.mapping变更为" + mapping);
            file.setMapping(mapping);
        }
    }

    @SneakyThrows
    public void ensureDirectoryExist() {
        FileProp file = coreProperties.getFile();
        FileUtils.mkdir(new java.io.File(file.getLocation() + DirectoryName.IMAGE_DIRECTORY), true);
    }

}
