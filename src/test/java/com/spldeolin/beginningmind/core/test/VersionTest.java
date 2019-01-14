package com.spldeolin.beginningmind.core.test;

import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.core.api.exception.BizException;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/19
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class VersionTest {

    @Autowired
    private UserService userService;

    @Test
    @SneakyThrows
    public void t() {
        UserEntity user = userService.get(285221975101440L).orElseThrow(() -> new BizException("不存在或是已被删除"));

        user.setUpdatedAt(LocalDateTime.of(1844, 1, 1, 2, 3, 4));
        user.setName("乐观锁2");
        user.setVersion(1);

        userService.update(user);

        log.info("结束");
    }

}