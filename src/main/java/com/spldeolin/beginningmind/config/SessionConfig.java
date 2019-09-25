package com.spldeolin.beginningmind.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Session保存时间、登录超时时间均在这里设置（单位：秒）
 *
 * @author Deolin
 */
@EnableRedisHttpSession(redisNamespace = "beginningMind",
        maxInactiveIntervalInSeconds = SessionConfig.SESSION_EXPIRE_SECONDS)
@Configuration
public class SessionConfig {

    public static final int SESSION_EXPIRE_SECONDS = 1801;

}
