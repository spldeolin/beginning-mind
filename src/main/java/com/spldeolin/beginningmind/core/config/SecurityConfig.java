package com.spldeolin.beginningmind.core.config;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.collect.Sets;
import com.spldeolin.beginningmind.core.CoreProperties;

/**
 * @author Deolin 2018/12/02
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private CoreProperties coreProperties;

    @Bean("anonUrls")
    public Set<String> anonUrls() {
        Set<String> urls = Sets.newHashSet();
        // 错误页面
        urls.add("/error");
        // 静态资源
        urls.add(coreProperties.getFile().getMapping());
        // 验证码请求、登录请求、测试
        urls.add("/sign/captcha");
        urls.add("/sign/in");
        urls.add("/sign/isSigning");
        return urls;
    }

    @Bean("authUrls")
    public Set<String> authUrls() {
        Set<String> urls = Sets.newHashSet();
        urls.add("/user/create");
        return urls;
    }

}
