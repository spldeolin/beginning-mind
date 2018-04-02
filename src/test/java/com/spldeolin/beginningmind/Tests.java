package com.spldeolin.beginningmind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.component.GlobalProperties;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class Tests {

    @Autowired
    private GlobalProperties globalProperties;

    @Test
    public void contextLoads() {
        log.info(globalProperties.getOneCookie());
    }

    @Test
    public void log() {
        log.error("遍历List算size");
        log.info("Jfinal两巨头");
        log.debug("插表王");
        log.warn("切面小王子");
    }

}
