package com.spldeolin.beginningmind.controller;

import java.util.concurrent.TimeUnit;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

/**
 * doc-ignore
 *
 * @author Deolin 2021-03-28
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("lock")
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

    @GetMapping("set")
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