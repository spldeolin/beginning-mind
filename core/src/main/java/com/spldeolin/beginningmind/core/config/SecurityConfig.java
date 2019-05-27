package com.spldeolin.beginningmind.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.spldeolin.beginningmind.core.security.holder.TokenHolder;
import com.spldeolin.beginningmind.core.service.SecurityAccessTokenService;

/**
 * 安全配置
 *
 * <pre>
 * 由于自Spring Boot 2.1.5起，项目必须依赖spring-boot-starter-security，否则自动报错，
 * 所以只能引入Spring Security，并通过配置禁用掉Spring Security。
 * </pre>
 *
 * @author Deolin 2018/12/02
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityAccessTokenService securityAccessTokenService;

    @Bean
    public TokenHolder tokenHolder() {
        TokenHolder tokenHolder = new TokenHolder();
        tokenHolder.holdTokens(securityAccessTokenService.listAll());
        return tokenHolder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }
}
