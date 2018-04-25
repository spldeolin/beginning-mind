package com.spldeolin.beginningmind.aop;

import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.component.ConfigurationBean;
import com.spldeolin.beginningmind.dto.CurrentSignUser;
import com.spldeolin.cadeau.library.util.RequestContextUtil;
import lombok.extern.log4j.Log4j2;

@Component
@Aspect
@Log4j2
public class ExpireReflashAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
    public void progress() {}

    @Before("progress()")
    public void before() {
        reflashSession();
        reflashSigning();
    }

    private void reflashSession() {
        // 无条件刷新一切会话
        HttpSession session = RequestContextUtil.session();
        session.setMaxInactiveInterval(ConfigurationBean.SESSION_EXPIRE_SECONDS);
    }

    private void reflashSigning() {
        Subject subject = SecurityUtils.getSubject();
        // 如果当前会话有人登录着，则刷新signer缓存
        if (subject.isAuthenticated() || subject.isRemembered()) {
            String username = ((CurrentSignUser) SecurityUtils.getSubject().getPrincipal()).getUsername();
            redisTemplate.expire("signer:" + username, ConfigurationBean.SESSION_EXPIRE_SECONDS, TimeUnit.SECONDS);
        }
    }

}
