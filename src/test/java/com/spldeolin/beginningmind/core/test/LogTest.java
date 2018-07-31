package com.spldeolin.beginningmind.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/07/31
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class LogTest {

    @Test
    @SneakyThrows
    public void t() {
        log.warn("123123");
        log.error("123123");
        log.debug("123123");
        log.info("结束");
    }

}