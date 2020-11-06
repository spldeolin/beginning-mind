package com.spldeolin.beginningmind;

import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2019/01/06
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
public class RedisTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void lock() {
        String key = "goods" + 1001L;

        RLock lock = redissonClient.getLock(key);
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }

        if (isLocked) {
            log.info("aaaa");
        }

        log.info("结束");
    }

    @Test
    public void set() {
        String key = "aaa";
        RBucket<String> bucket = redissonClient.getBucket(key);
        log.info(bucket.get());
        bucket.set("aaaa啊啊", 2, TimeUnit.SECONDS);
        log.info(bucket.get());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        log.info(bucket.get());
        bucket.set("汉字");
    }

}