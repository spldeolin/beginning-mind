/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.controller;

import javax.validation.Valid;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.controller.manager.SignManager;
import com.spldeolin.beginningmind.input.SignInput;
import com.spldeolin.beginningmind.service.SecurityAccountService;

/**
 * 登录、登出、踢出、登录状态等相关管理
 *
 * @author Deolin 2018/05/26
 */
@RestController
@RequestMapping("/sign")
@Validated
public class SignController {

    @Autowired
    private SignManager signManager;

    @Autowired
    private SecurityAccountService securityAccountService;

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    Object captcha() {
        return signManager.captcha();
    }

    /**
     * 登录
     */
    @PostMapping("/in")
    void signIn(@RequestBody @Valid SignInput input) {
        signManager.signIn(input);
    }

    /**
     * 登出
     */
    @PostMapping("/out")
    void signOut() {
        signManager.signOut();
    }

    /**
     * 当前调用者是否登录中
     */
    @GetMapping("/isSigning/current")
    Object isSign() {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated() || subject.isRemembered();
    }

    /**
     * 指定用户是否登录中
     */
    @GetMapping("/isSigning")
    Object isSign(@RequestParam Long accountId) {
        return securityAccountService.isAccountSigning(accountId);
    }

    /**
     * 将指定用户踢下线
     */
    @PostMapping("/kill")
    void kill(@RequestParam Long accountId) {
        securityAccountService.killSigner(accountId);
    }

}