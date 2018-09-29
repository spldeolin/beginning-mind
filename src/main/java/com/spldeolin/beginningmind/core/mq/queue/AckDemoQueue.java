package com.spldeolin.beginningmind.core.mq.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/09/28
 */
@Configuration
@Log4j2
public class AckDemoQueue {

    public static final String NAME = "ackDemo.queue";

    @Bean
    public Queue queue() {
        return new Queue(NAME);
    }

}
