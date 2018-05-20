package com.spldeolin.beginningmind.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.controller.manager.SignManager;
import com.spldeolin.beginningmind.input.SignInput;
import com.spldeolin.beginningmind.service.SecurityAccountService;

/**
 * 登录、登出、踢出、登录状态等相关管理
 *
 * @author Deolin
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
    public RequestResult captcha() {
        return RequestResult.success(signManager.captcha());
    }

    /**
     * 登录
     */
    @PostMapping("/in")
    public RequestResult signIn(@RequestBody @Valid SignInput input) {
        signManager.signIn(input);
        return RequestResult.success();
    }

    /**
     * 登出
     */
    @DeleteMapping("/out")
    public RequestResult signOut() {
        signManager.signOut();
        return RequestResult.success();
    }

    /**
     * 指定用户是否登录中
     */
    @GetMapping("/is_signing")
    public RequestResult isSign(@RequestParam("account_id") Long accountId) {
        return RequestResult.success(securityAccountService.isAccountSigning(accountId));
    }

    /**
     * 将指定用户踢下线
     */
    @DeleteMapping("/kill")
    //@RequiresPermissions("sign_kill")
    public RequestResult kill(@RequestParam("account_id") Long accountId) {
        securityAccountService.killSigner(accountId);
        return RequestResult.success();
    }

    /**
     * 匿名接口
     */
    @GetMapping("/anon")
    public RequestResult anon() {
        return RequestResult.success("初心");
    }

}