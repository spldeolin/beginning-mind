package com.spldeolin.beginningmind.core.config;

import javax.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import lombok.extern.log4j.Log4j2;

/**
 * RabbitMQ
 *
 * @author Deolin 2018/09/05
 */
@Configuration
@Log4j2
public class RabbitmqConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void rabbitTemplate() {

        rabbitTemplate.setReturnCallback(((message, replyCode, replyText, exchange, routingKey) -> {
            log.info(message);
            log.info(replyCode);
            log.info(replyText);
            log.info(exchange);
            log.info(routingKey);
        }));

        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> {
            if (ack) {
                log.info("成功");
            } else {
                log.info("失败 {}", cause);
            }
        }));
    }

}
