package com.spldeolin.beginningmind.core.setup;

import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/07/28
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class MongoChecker {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @SneakyThrows
    public void t() {
        for (int i = 0; i < 100; i++) {
            if (i == 50) {
                Thread.sleep(5000);
            }

            OperationLog operationLog = new OperationLog();
            operationLog.setNote("卫卫卫卫我iewewewewewewe" + i);
            operationLog.setLoggedAt(LocalDateTime.now());

            mongoTemplate.save(operationLog);
        }
        log.info("结束");
    }

    @Test
    @SneakyThrows
    public void ln53() {
        Query query = new Query();
        Criteria criteria = Criteria.where("loggedAt").gte(LocalDateTime.of(2018, 7, 28, 10, 45, 0))
                .lt(LocalDateTime.of(2019, 7, 28, 10, 46, 0));
        query.addCriteria(criteria);
        mongoTemplate.find(query, OperationLog.class).forEach(log::info);
    }

    @Document(collection = "operation_log")
    @Data
    private static class OperationLog {

        private String note;

        private LocalDateTime loggedAt;

    }

}