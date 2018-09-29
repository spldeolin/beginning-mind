package com.spldeolin.beginningmind.core.mq;

import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/09/28
 */
@Log4j2
public class AckUtils {

    public static void ack(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            log.info("ack 失败", e);
        }
    }

    public static void nack(Channel channel, Message message) {
        try {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        } catch (Exception e) {
            log.info("nack 失败", e);
        }
    }

}
