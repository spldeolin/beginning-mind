package com.spldeolin.beginningmind.core.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.controller.ErrorForwardController;
import com.spldeolin.beginningmind.core.model.SecurityPermission;
import com.spldeolin.beginningmind.core.security.filter.ActuatorFilter;
import com.spldeolin.beginningmind.core.security.SaltCredentialsMatcher;
import com.spldeolin.beginningmind.core.security.ServiceRealm;
import com.spldeolin.beginningmind.core.security.filter.AuthFilter;
import com.spldeolin.beginningmind.core.security.filter.SignFilter;
import com.spldeolin.beginningmind.core.security.TempTokenHolder;
import com.spldeolin.beginningmind.core.service.SecurityPermissionService;

@Configuration
public class ShiroConfig {

    @Value("${management.context-path}")
    private String actuatorUrlPrefix;

    @Autowired
    private CoreProperties coreProperties;

    @Autowired
    private TempTokenHolder tempTokenHolder;

    @Autowired
    private SecurityPermissionService securityPermissionService;

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
        filters.put(ActuatorFilter.MARK, new ActuatorFilter(tempTokenHolder));
        return filters;
    }

    /**
     * @return 过滤器链
     */
    private Map<String, String> createFilterChainDefinitions() {
        Map<String, String> filterChainDefinitions = new LinkedHashMap<>();
        // 【TOKEN】actuator相关请求使用TOKEN的过滤器
        filterChainDefinitions.put(actuatorUrlPrefix + "/**", ActuatorFilter.MARK);
        // 【匿名】放行error、静态资源、验证码请求、登录请求....
        filterChainDefinitions.put(ErrorForwardController.ERROR_PATH, "anon");
        filterChainDefinitions.put(coreProperties.getFile().getMapping() + "/**", "anon");
        filterChainDefinitions.put("/isSigning/current", "anon");
        filterChainDefinitions.put("/sign/captcha", "anon");
        filterChainDefinitions.put("/sign/in", "anon");
        // 【登录】登出请求是唯一一个无需权限、需要登录的请求
        filterChainDefinitions.put("/sign/out", SignFilter.MARK);
        // 【鉴权】为UrlForwardToExceptionController、TestController、SignController以外所有控制器 设置权限链
        for (SecurityPermission securityPermission : securityPermissionService.listAll()) {
            filterChainDefinitions.put(securityPermission.getMapping(),
                    SignFilter.MARK + ", " + AuthFilter.MARK + "[" + securityPermission.getName() + "]");
        }
        // DEBUG环境下放行一切请求（不进行任何认证和鉴权的访问控制）
        if (coreProperties.isDebug()) {
            filterChainDefinitions = new LinkedHashMap<>();
            filterChainDefinitions.put("/**", "anon");
        }
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
        realm.setCredentialsMatcher(new SaltCredentialsMatcher());
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
