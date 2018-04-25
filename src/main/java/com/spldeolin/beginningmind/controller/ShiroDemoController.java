package com.spldeolin.beginningmind.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.component.ConfigurationBean;
import com.spldeolin.beginningmind.dto.CurrentSignUser;
import com.spldeolin.beginningmind.input.SignInInput;
import com.spldeolin.cadeau.library.cache.RedisCache;
import com.spldeolin.cadeau.library.dto.RequestResult;
import com.spldeolin.cadeau.library.exception.ServiceException;
import com.spldeolin.cadeau.library.util.RequestContextUtil;

@RestController
@RequestMapping("shiro")
public class ShiroDemoController {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    FindByIndexNameSessionRepository<? extends ExpiringSession> sessionRepository;

    /**
     * 登录
     */
    @PostMapping("sign_in")
    public RequestResult signIn(@RequestBody SignInInput input) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(input.getUsername(), input.getPassword());
            token.setRememberMe(false);
            try {
                subject.login(token);
                // 添加signer缓存
                redisCache.setCacheWithExpireTime("signer:" + input.getUsername(), RequestContextUtil.session().getId(),
                        ConfigurationBean.SESSION_EXPIRE_SECONDS);
            } catch (AuthenticationException e) {
                throw new ServiceException(e.getMessage());
            }
        } else {
            throw new ServiceException("请勿重复登录");
        }
        return RequestResult.success();
    }

    /**
     * 登出
     */
    @RequiresAuthentication
    @PostMapping("sign_out")
    public RequestResult signOut() {
        SecurityUtils.getSubject().logout();
        return RequestResult.success();
    }

    /**
     * 获取当前登录者信息
     */
    @GetMapping("signer")
    public RequestResult signer() {
        Session session = SecurityUtils.getSubject().getSession();
        Map<String, Object> map = new HashMap<>();
        String username = ((CurrentSignUser) SecurityUtils.getSubject().getPrincipal()).getUsername();
        map.put("用户名", username);
        map.put("在线时长（分钟）", ChronoUnit.MINUTES.between(
                session.getStartTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                LocalDateTime.now()
        ));
        return RequestResult.success(map);
    }

}
