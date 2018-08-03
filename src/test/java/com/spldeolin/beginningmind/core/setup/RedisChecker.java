package com.spldeolin.beginningmind.core.setup;

import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/08/01
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class RedisChecker {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @SneakyThrows
    public void t() {
        redisTemplate.opsForValue().set("one-cookie", "曲奇", 10, TimeUnit.SECONDS);

        log.info(redisTemplate.opsForValue().get("one-cookie"));

        redisTemplate.delete("one-cookie"); //break point

        log.info("结束");
    }

}