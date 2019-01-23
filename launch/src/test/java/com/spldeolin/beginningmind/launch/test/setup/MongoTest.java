package com.spldeolin.beginningmind.launch.test.setup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2019-01-23
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void t() throws Exception {
        Query query = Query.query(Criteria.where("level").is("INFO"));
        log.info(mongoTemplate.count(query, "log_unknown"));
    }

}