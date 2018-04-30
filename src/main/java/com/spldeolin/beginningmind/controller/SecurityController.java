package com.spldeolin.beginningmind.controller;

import java.util.Collection;
import javax.validation.Valid;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.cache.RedisCache;
import com.spldeolin.beginningmind.config.SessionConfig;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.input.SignInInput;
import com.spldeolin.beginningmind.util.RequestContextUtil;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("security")
@Log4j2
@Validated
public class SecurityController {

    @PostMapping("sign_in")
    public RequestResult signIn(@RequestBody @Valid SignInInput input) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(input.getUsername(), input.getPassword());
            token.setRememberMe(false);
            try {
                subject.login(token);
                RequestContextUtil.session().setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME,
                        input.getUsername());
            } catch (AuthenticationException e) {
                throw new ServiceException(e.getMessage());
            }
        } else {
            throw new ServiceException("请勿重复登录");
        }
        return RequestResult.success();
    }

    @PostMapping("sign_out")
    public RequestResult signOut() {
        SecurityUtils.getSubject().logout();
        return RequestResult.success();
    }

    @GetMapping("signer")
    public RequestResult signer() {
        return RequestResult.success(SecurityUtils.getSubject().getPrincipal());
    }

    @Autowired
    private FindByIndexNameSessionRepository<? extends Session> finder;

    @Autowired
    private RedisCache redisCache;

    @GetMapping("kill")
    public RequestResult kill(@RequestParam String username) {
        Collection<? extends Session> signerSessions = this.finder.findByIndexNameAndIndexValue(
                FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username).values();
        // 只可能找到一个，或找不到
        if (signerSessions.size() > 0) {
            // PRINCIPAL_NAME_INDEX_NAME不存在的请求，会被下一次请求的切面主动subject.logout();
            Session session = signerSessions.toArray(new Session[0])[0];
            redisCache.setCacheWithExpireTime("killed:session:" + session.getId(), "killed",
                    SessionConfig.SESSION_EXPIRE_SECONDS);
        }
        return RequestResult.success();
    }

}
