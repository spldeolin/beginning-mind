package com.spldeolin.beginningmind.core.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Deolin 2018/09/05
 */
@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue Queue() {
        return new Queue("hello");
    }

}
