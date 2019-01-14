package com.spldeolin.beginningmind.core.setup;

import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.redis.RedisCache;
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
    private RedisCache redisCache;

    @Test
    @SneakyThrows
    public void t() {
        redisCache.set("one-cookie", UserEntity.builder().name("曲奇").build(), 100, TimeUnit.SECONDS);

        UserEntity user = redisCache.get("one-cookie");
        log.info(user);

        redisCache.delete("one-cookie"); //break point

        log.info("结束");
    }

}