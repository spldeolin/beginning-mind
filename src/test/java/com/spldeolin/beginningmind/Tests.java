package com.spldeolin.beginningmind;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spldeolin.beginningmind.cache.RedisCache;
import com.spldeolin.beginningmind.config.BeginningMindProperties;
import com.spldeolin.beginningmind.dao.bm1.BuyerMapper;
import com.spldeolin.beginningmind.dao.bm2.ExMapper;
import com.spldeolin.beginningmind.model.Goods;
import com.spldeolin.beginningmind.model.SecurityAccount;
import com.spldeolin.beginningmind.model.Seller;
import com.spldeolin.beginningmind.service.SecurityAccountService;
import com.spldeolin.beginningmind.util.ApplicationContext;
import com.spldeolin.beginningmind.util.JSONUtils;
import com.spldeolin.beginningmind.util.StringRandomUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class Tests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BeginningMindProperties beginningMindProperties;

    @Autowired
    private SecurityAccountService securityAccountService;

    public static void main(String[] args) {
        System.out.println(Goods.builder().name("曲奇饼干").build());
    }

    @Test
    public void contextLoads() {
        log.info(beginningMindProperties);
        log.info(ApplicationContext.getBean(BeginningMindProperties.class));
    }

    @Test
    public void generateAccountsPasswordAndSalt() {
        String rawPassword = "000000";
        String passwordEX = DigestUtils.sha512Hex(rawPassword);
        List<SecurityAccount> accounts = securityAccountService.listAll();
        for (SecurityAccount account : accounts) {
            String salt = StringRandomUtils.generateVisibleAscii(32);
            String passwordEX2 = DigestUtils.sha512Hex(passwordEX + salt);
            account.setSalt(salt).setPassword(passwordEX2);
            securityAccountService.update(account);
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
        mongoTemplate.save(Goods.builder().id(1L).name("Deolin   汉字").build());
    }

    @Test
    public void test2() {
        List<Goods> users = mongoTemplate.find(new Query().addCriteria(Criteria.where("id").is(1L)), Goods.class);
        log.info(users);
    }

    @Test
    public void testJsonUtil() {
        ObjectMapper om = new ObjectMapper();
        Goods user = Goods.builder().name("aaa_bbb").updatedAt(LocalDateTime.MAX).build();
        log.info(JSONUtils.toJson(user));
        log.info(JSONUtils.toJson(user, om));

        String json = "{\"id\":null,\"updatedAt\":\"+999999999-12-31 23:59:59\",\"name\":\"aaa_bbb\",\"salt\":null,\"sex\":null,\"age\":null,\"flag\":null,\"ymd\":null,\"hms\":null,\"ymdhms\":null,\"money\":null,\"serialNumber\":null,\"percent\":null,\"richText\":null}";
        String snakeJson = "{\"id\":null,\"updated_at\":\"+999999999-12-31 23:59:59\",\"name\":\"aaa_bbb\",\"salt\":null,\"sex\":null,\"age\":null,\"flag\":null,\"ymd\":null,\"hms\":null,\"ymdhms\":null,\"money\":null,\"serial_number\":null,\"percent\":null,\"rich_text\":null}";
        log.info(JSONUtils.toObject(json, Goods.class));
        log.info(JSONUtils.toObject(json, Goods.class, om));
        log.info(JSONUtils.toObject(snakeJson, Goods.class));
        log.info(JSONUtils.toObject(snakeJson, Goods.class, om));
    }

    @Test
    @SneakyThrows
    public void io() {
        BeginningMindProperties properties = ApplicationContext.getBean(BeginningMindProperties.class);
        FileUtils.writeStringToFile(new File(properties.getFile().getLocation() + File.separator + "a.txt"), "你好",
                StandardCharsets.UTF_8);
        log.info("映射路径" + properties.getFile().getMapping() + "/a.txt");
        log.info("hold on");
    }

    @Autowired
    private ExMapper exMapper;

    @Test
    public void bm2() {
        log.info(exMapper.names());
    }

    @Autowired
    private BuyerMapper buyerMapper;

    @Test
    public void bm1() {
        log.info(buyerMapper.selectAll());
    }

    @Autowired
    private RedisCache redisCache;

    @Test
    public void redis() {
        redisCache.setCacheWithExpireTime("b", Seller.builder().nickname("鬼方").build(), 500);
    }

}
