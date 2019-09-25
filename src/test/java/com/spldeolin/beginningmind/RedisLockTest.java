package com.spldeolin.beginningmind;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.redis.RedisLock;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2019/01/06
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class RedisLockTest {

    @Autowired
    private RedisLock redisLock;

    @Test
    public void lock() {
        String key = "goods" + 1001L;
        String value = UUID.randomUUID().toString();

        log.info(redisLock.lock(key, value, 50_000));
        log.info(redisLock.lock(key, value, 60_000));
        log.info(redisLock.lock(key, UUID.randomUUID().toString(), 50_000));

        log.info("结束");
    }

}