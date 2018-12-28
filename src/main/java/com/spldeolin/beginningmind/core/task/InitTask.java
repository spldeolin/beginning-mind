package com.spldeolin.beginningmind.core.task;

import java.net.InetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.CoreProperties;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * 项目刚启动时的任务
 *
 * @author Deolin 2018/11/16
 */
@Component
@Log4j2
public class InitTask implements CommandLineRunner {

    @Autowired
    private CoreProperties coreProperties;

    @Override
    public void run(String... args) {
        // 将配置中出现的所有localhost替换为本地IP
        localhostToLocalIp();
    }

    @SneakyThrows
    public void localhostToLocalIp() {
        String address = coreProperties.getAddress();
        address = address.replace("localhost", InetAddress.getLocalHost().getHostAddress());
        coreProperties.setAddress(address);
    }

}
