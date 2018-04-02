package com.spldeolin.beginningmind.component;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Component;
import com.spring4all.swagger.EnableSwagger2Doc;

@Component
@Configuration
@EnableSwagger2Doc
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 1800)
public class ExtraConfiguration {



}