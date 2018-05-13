package com.spldeolin.beginningmind.message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.model.Goods;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class Customer {

    @JmsListener(destination = "onecookie.queue")
    public void receiveQueue(String text) {
        log.info("收到曲奇饼干：" + text);
    }

    @JmsListener(destination = "cheese.queue")
    public void rq(Goods goods) {
        log.info("收到芝士：" + goods);
    }

}
