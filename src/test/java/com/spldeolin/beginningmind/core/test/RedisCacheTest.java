package com.spldeolin.beginningmind.core.test;

import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.redis.RedisCache;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/08/10
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class RedisCacheTest {

    @Autowired
    private RedisCache redisCache;

    @Test
    @SneakyThrows
    public void ln26() {
        redisCache.set("test:d1", "汉字21");
        redisCache.set("test:d2", "汉字22");
        redisCache.set("test:d3", "汉字23");
        redisCache.set("test:d4", "汉字24");
        redisCache.set("test:d5", "汉字25");
        redisCache.set("test:d6", "汉字26");
        redisCache.set("test:d7", "汉字27");
        redisCache.set("test:d8", "汉字28");
        redisCache.set("test:d9", "汉字29");
    }

    @Test
    @SneakyThrows
    public void ln42() {
        redisCache.delete("test:d8");
    }

    @Test
    @SneakyThrows
    public void ln48() {
        redisCache.delete(Lists.newArrayList("test:d8"));
    }

    @Test
    @SneakyThrows
    public void ln55() {
        log.info(redisCache.isExist("test:d8"));
    }

    @Test
    @SneakyThrows
    public void ln61() {
        log.info(redisCache.updateExpire("test:d9", 1, TimeUnit.SECONDS));
        log.info(redisCache.updateExpire("test:d8", 1, TimeUnit.SECONDS));
    }

    @Test
    @SneakyThrows
    public void ln69() {
        redisCache.searchKeys("test:*").forEach(log::info);
    }

    @Test
    @SneakyThrows
    public void ln75() {
        log.info(redisCache.move("test:d7", 1));
    }

}