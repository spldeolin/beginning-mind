package com.spldeolin.beginningmind.core.config;

import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.collect.Sets;

/**
 * 安全配置
 *
 * @author Deolin 2018/12/02
 */
@Configuration
public class SecurityConfig {

    /**
     * 以返回值中字符串开头的uri对应的请求，可以匿名访问
     */
    @Bean("anonUrlsPrefix")
    public Set<String> anonUrlsPrefix() {
        Set<String> urls = Sets.newHashSet();
        // 错误页面
        urls.add("/error");
        // 验证码请求、登录请求、测试
        urls.add("/sign/captcha");
        urls.add("/sign/in");
        urls.add("/sign/isSigning");
        urls.add("/test");
        return urls;
    }

    @Bean("authUrls")
    public Set<String> authUrls() {
        Set<String> urls = Sets.newHashSet();
        urls.add("/user/create");
        return urls;
    }

}
