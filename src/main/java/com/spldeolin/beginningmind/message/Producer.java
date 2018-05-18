package com.spldeolin.beginningmind.message;

import javax.jms.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.model.Goods;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("oneCookieQueue")
    private Queue queue;

    @Autowired
    @Qualifier("cheeseQueue")
    private Queue cheeseQueue;

    //@Scheduled(fixedDelay = 3000)
    public void send() {
        log.info("准备发送一个曲奇饼干与芝士");
        jmsMessagingTemplate.convertAndSend(queue, "队列中的曲奇饼干");
        jmsMessagingTemplate.convertAndSend(cheeseQueue, Goods.builder().name("芝士").build());
    }

}
