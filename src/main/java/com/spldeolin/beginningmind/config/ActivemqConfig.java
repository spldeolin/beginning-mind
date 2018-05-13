package com.spldeolin.beginningmind.config;

import javax.annotation.PostConstruct;
import javax.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beust.jcommander.internal.Lists;

@Configuration
public class ActivemqConfig {

    @Autowired
    private ActiveMQConnectionFactory activeMQConnectionFactory;

    @PostConstruct
    public void setTrustedPackages() {
        activeMQConnectionFactory.setTrustedPackages(Lists.newArrayList("com.spldeolin.beginningmind"));
    }

    @Bean("oneCookieQueue")
    public Queue oneCookieQueue() {
        return new ActiveMQQueue("onecookie.queue");
    }

    @Bean("cheeseQueue")
    public Queue cheeseQueue() {
        return new ActiveMQQueue("cheese.queue");
    }

}
