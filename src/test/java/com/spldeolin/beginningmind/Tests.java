package com.spldeolin.beginningmind;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spldeolin.beginningmind.model.User;
import com.spldeolin.beginningmind.component.Properties;
import com.spldeolin.beginningmind.util.JsonUtil;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class Tests {

    @Autowired
    private Properties properties;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void contextLoads() {
        for (Field field : Properties.class.getDeclaredFields()) {
            field.setAccessible(true);
            log.info(ReflectionUtils.getField(field, properties));
        }
        for (Field field : Properties.TimeProperties.class.getDeclaredFields()) {
            field.setAccessible(true);
            log.info(ReflectionUtils.getField(field, properties));
        }
        for (Field field : Properties.TextZhHansProperties.class.getDeclaredFields()) {
            field.setAccessible(true);
            log.info(ReflectionUtils.getField(field, properties));
        }
        for (Field field : Properties.TextJaJpProperties.class.getDeclaredFields()) {
            field.setAccessible(true);
            log.info(ReflectionUtils.getField(field, properties));
        }
        for (Field field : Properties.WechatProperties.class.getDeclaredFields()) {
            field.setAccessible(true);
            log.info(ReflectionUtils.getField(field, properties));
        }
        for (Field field : Properties.AlidayuProperties.class.getDeclaredFields()) {
            field.setAccessible(true);
            log.info(ReflectionUtils.getField(field, properties));
        }
    }

    @Test
    public void log() {
        log.error("e");
        log.info("i");
        log.debug("d");
        log.warn("e");
    }

    @Test
    public void test() {
        mongoTemplate.save(User.builder().id(1L).name("Deolin   汉字").build());
    }

    @Test
    public void test2() {
        List<User> users = mongoTemplate.find(new Query().addCriteria(Criteria.where("id").is(1L)), User.class);
        log.info(users);
    }

    @Test
    public void testJsonUtil() {
        ObjectMapper om = new ObjectMapper();
        JsonUtil.commonConfig(om);
        JsonUtil.timeConfig(om);
        User user = User.builder().name("aaa_bbb").updatedAt(LocalDateTime.MAX).build();
        log.info(JsonUtil.toJson(user));
        log.info(JsonUtil.toJson(user, om));

        String json = "{\"id\":null,\"updatedAt\":\"+999999999-12-31 23:59:59\",\"name\":\"aaa_bbb\",\"salt\":null,\"sex\":null,\"age\":null,\"flag\":null,\"ymd\":null,\"hms\":null,\"ymdhms\":null,\"money\":null,\"serialNumber\":null,\"percent\":null,\"richText\":null}";
        String snakeJson = "{\"id\":null,\"updated_at\":\"+999999999-12-31 23:59:59\",\"name\":\"aaa_bbb\",\"salt\":null,\"sex\":null,\"age\":null,\"flag\":null,\"ymd\":null,\"hms\":null,\"ymdhms\":null,\"money\":null,\"serial_number\":null,\"percent\":null,\"rich_text\":null}";
        log.info(JsonUtil.toObject(json, User.class));
        log.info(JsonUtil.toObject(json, User.class, om));
        log.info(JsonUtil.toObject(snakeJson, User.class));
        log.info(JsonUtil.toObject(snakeJson, User.class, om));
    }

}
