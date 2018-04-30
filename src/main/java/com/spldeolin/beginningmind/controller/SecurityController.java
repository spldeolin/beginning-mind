package com.spldeolin.beginningmind.controller;

import javax.validation.Valid;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.input.SignInInput;

@RestController
@RequestMapping("security")
public class SecurityController {

    @PostMapping("sign_in")
    public RequestResult signIn(@RequestBody @Valid SignInInput input) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(input.getUsername(), input.getPassword());
            token.setRememberMe(false);
            try {
                subject.login(token);
                // TODO 登录成功后，添加signer缓存
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

}
