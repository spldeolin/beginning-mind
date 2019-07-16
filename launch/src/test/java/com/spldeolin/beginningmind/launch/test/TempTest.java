package com.spldeolin.beginningmind.launch.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.core.service.BbbService;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2019-07-15
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class TempTest {

    @Autowired
    private BbbService bbbService;

    @Test
    public void t25() throws Exception {
        bbbService.createAndCreate();
    }

}