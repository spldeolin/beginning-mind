package com.spldeolin.beginningmind.controller;

import com.spldeolin.beginningmind.input.SignInInput;
import com.spldeolin.cadeau.library.dto.RequestResult;
import com.spldeolin.cadeau.library.exception.ServiceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shiro")
public class ShiroDemoController {

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
     * 登录后才能访问的内部请求示例
     */
    @GetMapping("need_sign_in")
    public RequestResult needSignIn() {
        return RequestResult.success("登录后才能看到这句话");
    }

}
