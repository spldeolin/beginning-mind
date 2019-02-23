package com.spldeolin.beginningmind.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.spldeolin.beginningmind.core.security.holder.TokenHolder;
import com.spldeolin.beginningmind.core.service.SecurityAccessTokenService;

/**
 * 安全配置
 *
 * @author Deolin 2018/12/02
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityAccessTokenService securityAccessTokenService;

    @Bean
    public TokenHolder tokenHolder() {
        TokenHolder tokenHolder = new TokenHolder();
        tokenHolder.holdTokens(securityAccessTokenService.listAll());
        return tokenHolder;
    }

}
