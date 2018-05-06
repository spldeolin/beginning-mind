package com.spldeolin.beginningmind.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.spldeolin.beginningmind.constant.CoupledConstant;
import com.spldeolin.beginningmind.security.SaltSha512CredentialsMatcher;
import com.spldeolin.beginningmind.security.ServiceRealm;

@Configuration
public class SecurityConfig {

    @Autowired
    private Environment environment;

    @Value("${management.context-path}")
    private String actuatorContextPath;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 重定向到RestController
        shiroFilterFactoryBean.setLoginUrl("/unauthc");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 放行验证码请求、登录请求、error页面、静态资源.... 以及一些临时测试的简单请求
        filterChainDefinitionMap.put("/sign/verify_code", "anon");
        filterChainDefinitionMap.put("/sign/in", "anon");
        filterChainDefinitionMap.put(CoupledConstant.ERROR_PAGE_URL, "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/sign/anon", "anon");
        if (!ArrayUtils.contains(environment.getActiveProfiles(), CoupledConstant.PROD_PROFILE_NAME)) {
            // 非prod环境放行actuator相关请求
            if (StringUtils.isNotBlank(actuatorContextPath)) {
                filterChainDefinitionMap.put(actuatorContextPath + "/**", "anon");
            }
            // 非prod环境放行swagger相关请求
            for (String swaggerUrlMatchingPrefix : CoupledConstant.SWAGGER_URL_MATCHING_PREFIXES) {
                filterChainDefinitionMap.put(swaggerUrlMatchingPrefix + "**", "anon");
            }
        }
        filterChainDefinitionMap.put("/**", "authc");
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
    public AuthorizingRealm realm(CacheManager cacheManager) {
        AuthorizingRealm realm = new ServiceRealm();
        realm.setCredentialsMatcher(new SaltSha512CredentialsMatcher());
        realm.setCacheManager(cacheManager);
        return realm;
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

    /**
     * 开启@RequiresPermissions注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
