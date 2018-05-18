package com.spldeolin.beginningmind.config;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
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
import com.spldeolin.beginningmind.constant.CoupledConstant;
import com.spldeolin.beginningmind.controller.UrlForwardToExceptionController;
import com.spldeolin.beginningmind.security.ActuatorFilter;
import com.spldeolin.beginningmind.security.SaltSha512CredentialsMatcher;
import com.spldeolin.beginningmind.security.ServiceRealm;
import com.spldeolin.beginningmind.security.SignFilter;

@Configuration
public class ShiroConfig {

    @Value("${management.context-path}")
    private String actuatorUrlPrefix;

    @Autowired
    private BeginningMindProperties properties;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 过滤器
        shiroFilterFactoryBean.setFilters(createFilters());
        // 过滤器链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(createFilterChainDefinitions());
        return shiroFilterFactoryBean;
    }

    /**
     * @return 过滤器一览
     */
    private Map<String, Filter> createFilters() {
        Map<String, Filter> filters = new HashMap<>();
        filters.put(SignFilter.MARK, new SignFilter());
        filters.put(ActuatorFilter.MARK, new ActuatorFilter());
        return filters;
    }

    /**
     * @return 过滤器链
     */
    private Map<String, String> createFilterChainDefinitions() {
        Map<String, String> filterChainDefinitions = new HashMap<>();
        // 放行error、静态资源、验证码请求、登录请求.... 以及一些临时测试的简单请求
        filterChainDefinitions.put(UrlForwardToExceptionController.ERROR_PATH, "anon");
        filterChainDefinitions.put(UrlForwardToExceptionController.SHIROFILTER_LOGINURL_URL, "anon");
        filterChainDefinitions.put(UrlForwardToExceptionController.UNAUTHORIZED_URL, "anon");
        filterChainDefinitions.put(properties.getFile().getMapping() + "/**", "anon");
        filterChainDefinitions.put("/sign/captcha", "anon");
        filterChainDefinitions.put("/sign/in", "anon");
        filterChainDefinitions.put("/sign/anon", "anon");
        // 放行swagger相关请求（swagger配置中可以根据profile决定是否启用）
        for (String swaggerUrlPrefix : CoupledConstant.SWAGGER_URL_PREFIXES) {
            filterChainDefinitions.put(swaggerUrlPrefix + "**", "anon");
        }
        // actuator相关请求使用专门的过滤器
        filterChainDefinitions.put(actuatorUrlPrefix + "/**", ActuatorFilter.MARK);
        // 其他请求默认闭环
        filterChainDefinitions.put("/**", SignFilter.MARK);
        return filterChainDefinitions;
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
