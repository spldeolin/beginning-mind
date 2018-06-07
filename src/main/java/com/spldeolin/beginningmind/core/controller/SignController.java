/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.core.controller;

import javax.validation.Valid;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.input.SignInput;
import com.spldeolin.beginningmind.core.service.SignService;

/**
 * 登录、登出、登录状态等
 *
 * @author Deolin 2018/05/26
 */
@RestController
@RequestMapping("/sign")
@Validated
public class SignController {

    @Autowired
    private SignService signService;

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    Object captcha() {
        return signService.captcha();
    }

    /**
     * 登录
     */
    @PostMapping("/in")
    Object signIn(@RequestBody @Valid SignInput input) {
        return signService.signIn(input);
    }

    /**
     * 登出
     */
    @PostMapping("/out")
    Object signOut() {
        signService.signOut();
        return null;
    }

    /**
     * 当前调用者是否登录中
     */
    @GetMapping("/isSigning")
    Object isSign() {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated() || subject.isRemembered();
    }

}