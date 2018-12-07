package com.spldeolin.beginningmind.core.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.dto.SignerProfileVO;
import com.spldeolin.beginningmind.core.input.SignInput;
import com.spldeolin.beginningmind.core.security.util.Signer;
import com.spldeolin.beginningmind.core.service.SignService;

/**
 * 登录、登出、登录状态等
 *
 * @author Deolin 2018/05/26
 */
@RestController
@Validated
public class SignController {

    @Autowired
    private SignService signService;

    /**
     * 获取验证码
     */
    @GetMapping("/sign/captcha")
    String captcha() {
        return signService.captcha();
    }

    /**
     * 登录
     */
    @PostMapping("/sign/in")
    SignerProfileVO signIn(@RequestBody @Valid SignInput input) {
        return signService.signIn(input);
    }

    /**
     * 登出
     */
    @PostMapping("/sign/out")
    void signOut() {
        signService.signOut();
    }

    /**
     * 当前调用者是否登录中
     */
    @GetMapping("/sign/isSigning")
    Boolean isSigning() {
        return Signer.isSigning();
    }

}