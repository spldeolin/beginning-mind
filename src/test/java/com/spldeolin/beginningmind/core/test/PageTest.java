package com.spldeolin.beginningmind.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.api.dto.PageParam;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.service.UserService;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/10
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class PageTest {

    @Autowired
    private UserService userService;

    @Test
    public void t() {
        PageParam pageParam = new PageParam();
        pageParam.startPage();
        Page<User> userPage = Page.wrap(userService.listAll());
        log.info(userPage);
    }


}