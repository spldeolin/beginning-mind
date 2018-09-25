package com.spldeolin.beginningmind.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.core.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/09/22
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class CommonServiceCacheTest {

    @Autowired
    private UserService userService;

    @Test
    @SneakyThrows
    public void t() {
        userService.get(1L);
        log.info("结束");
    }

}