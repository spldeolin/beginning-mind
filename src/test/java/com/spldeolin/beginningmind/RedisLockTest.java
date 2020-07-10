package com.spldeolin.beginningmind;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.redis.RedisLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2019/01/06
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
public class RedisLockTest {

    @Autowired
    private RedisLock redisLock;

    @Test
    public void lock() {
        String key = "goods" + 1001L;
        String value = UUID.randomUUID().toString();

        log.info("a={}", redisLock.lock(key, value, 50_000));
        log.info("b={}", redisLock.lock(key, value, 60_000));
        log.info("c={}", redisLock.lock(key, UUID.randomUUID().toString(), 50_000));

        log.info("结束");
    }

}