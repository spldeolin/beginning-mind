package com.spldeolin.beginningmind.core.mq.producer;

import java.time.LocalDateTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.mq.queue.AckDemoQueue;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/09/28
 */
@Component
@Log4j2
public class AckDemoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void produce(String message) {
        rabbitTemplate.setMandatory(true);
        User user = User.builder().name(message).serialNumber("测试用").updatedAt(LocalDateTime.now()).build();
        rabbitTemplate.convertAndSend(AckDemoQueue.NAME, user);
    }

}
