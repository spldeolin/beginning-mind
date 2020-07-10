package com.spldeolin.beginningmind;

import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.entity.UserEntity;
import com.spldeolin.beginningmind.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2018/11/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
public class VersionTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void t() {
        UserEntity user = userMapper.selectById(285221975101440L);

        user.setUpdatedAt(LocalDateTime.of(1844, 1, 1, 2, 3, 4));
        user.setName("乐观锁2");
        user.setVersion(1);

        userMapper.updateById(user);

        log.info("结束");
    }

}