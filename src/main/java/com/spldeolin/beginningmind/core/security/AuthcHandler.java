package com.spldeolin.beginningmind.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.service.SignService;

/**
 * @author Deolin 2018/12/02
 */
@Component
public class AuthcHandler {

    @Autowired
    private SignService signService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


}
