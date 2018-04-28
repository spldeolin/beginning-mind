package com.spldeolin.beginningmind.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import com.spldeolin.beginningmind.component.shiro.CredentialsMatcher;
import com.spldeolin.beginningmind.component.shiro.Realm;

public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 不再重定向到登录页面，而是返回前端code401
        shiroFilterFactoryBean.setLoginUrl("/unauthc");
        // 不再重定向到未授权页面，而是返回前端code403
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/shiro/signer", "authc");
        filterChainDefinitionMap.put("/shiro/sign_out", "authc");
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(AuthorizingRealm realm, CacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    @Bean
    public AuthorizingRealm realm(SimpleCredentialsMatcher simpleCredentialsMatcher, CacheManager cacheManager) {
        AuthorizingRealm realm = new Realm();
        realm.setCredentialsMatcher(simpleCredentialsMatcher);
        realm.setCacheManager(cacheManager);
        return realm;
    }

    @Bean
    public SimpleCredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean
    public RedisCacheManager cacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    @Bean
    public RedisManager redisManager(
            @Value("${spring.redis.host}") String host,
            @Value("${spring.redis.port}") int port,
            @Value("${spring.redis.timeout}") int timeout,
            @Value("${spring.redis.password}") String password) {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }

}
