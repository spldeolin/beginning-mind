package com.spldeolin.beginningmind.core.setup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.service.SnowFlakeService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/08/01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class CorePropertiesChecker {

    @Autowired
    private CoreProperties coreProperties;

    @Test
    @SneakyThrows
    public void t() {
        log.info(coreProperties);
        log.info("结束");
    }

    @Autowired
    private SnowFlakeService snowFlakeService;

    @Test
    @SneakyThrows
    public void ln34() {
        for (int i = 0; i < 100000; i++) {
            log.info(snowFlakeService.nextId());
        }
    }

}