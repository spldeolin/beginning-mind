package com.spldeolin.beginningmind.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2019-04-23
 */
@Log4j2
@Component
public class TimeTask {

    @Scheduled(fixedDelay = 5000)
    public void report() {
        ThreadContext.put("async", "[TT-" + StringRandomUtils.generateLegibleEnNum(3) + "]");


        log.info("啊");
        ThreadContext.remove("async");
    }

}
