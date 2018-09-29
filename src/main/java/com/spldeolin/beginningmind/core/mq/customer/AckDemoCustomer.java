package com.spldeolin.beginningmind.core.mq.customer;

import java.io.IOException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.mq.queue.AckDemoQueue;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/09/28
 */
@Component
@RabbitListener(queues = AckDemoQueue.NAME)
@Log4j2
public class AckDemoCustomer {

    @RabbitHandler
    public void consume(User user, Channel channel, Message message) throws IOException {
        if (user.getName().length() > 10) {
            throw new ServiceException("asdf");
        }

        log.info("消费 {}", user);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
