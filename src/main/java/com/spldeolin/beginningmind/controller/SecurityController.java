package com.spldeolin.beginningmind.controller;

import javax.validation.Valid;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.input.SignInInput;
import com.spldeolin.beginningmind.service.SecurityAccountService;
import com.spldeolin.beginningmind.util.RequestContextUtil;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("security")
@Log4j2
@Validated
public class SecurityController {

    @Autowired
    private SecurityAccountService securityAccountService;

    /**
     * 登录
     */
    @PostMapping("sign_in")
    public RequestResult signIn(@RequestBody @Valid SignInInput input) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            throw new ServiceException("请勿重复登录");
        }
        try {
            subject.login(input.toToken());
        } catch (AuthenticationException e) {
            throw new ServiceException(e.getMessage());
        }
        // 用于定位当前会话
        RequestContextUtil.session().setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME,
                input.getUsername());
        return RequestResult.success();
    }

    /**
     * 登出
     */
    @DeleteMapping("sign_out")
    public RequestResult signOut() {
        SecurityUtils.getSubject().logout();
        return RequestResult.success();
    }

    /**
     * 指定用户是否登录中
     */
    @GetMapping("is_sign")
    public RequestResult isSign(@RequestParam("account_id") Long accountId) {
        return RequestResult.success(securityAccountService.isAccountSigning(accountId));
    }

    /**
     * 将指定用户踢下线
     */
    @DeleteMapping("kill")
    public RequestResult kill(@RequestParam("account_id") Long accountId) {
        securityAccountService.killSigner(accountId);
        return RequestResult.success();
    }

}
