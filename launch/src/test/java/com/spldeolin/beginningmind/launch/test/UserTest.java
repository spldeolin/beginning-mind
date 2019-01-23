package com.spldeolin.beginningmind.launch.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.core.service.UserService;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/19
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void searchOneByPrincipal() {
        log.info(userService.searchOneByPrincipal("aa@a"));
        log.info("结束");
    }

}