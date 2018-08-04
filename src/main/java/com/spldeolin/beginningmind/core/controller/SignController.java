/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.core.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.input.SignInput;
import com.spldeolin.beginningmind.core.service.SignService;
import com.spldeolin.beginningmind.core.util.Signer;
import com.spldeolin.beginningmind.core.vo.SignerProfileVO;

/**
 * 登录、登出、登录状态等
 *
 * @author Deolin 2018/05/26
 */
@RestController
@Validated
public class SignController {

    public static final String CAPTCHA_REQUEST_MAPPING = "/sign/captcha";

    public static final String SIGN_IN_REQUEST_MAPPING = "/sign/in";

    public static final String SIGN_OUT_REQUEST_MAPPING = "/sign/out";

    public static final String IS_SIGNING_REQUEST_MAPPING = "/sign/isSigning";

    @Autowired
    private SignService signService;

    /**
     * 获取验证码
     */
    @GetMapping(CAPTCHA_REQUEST_MAPPING)
    String captcha() {
        return signService.captcha();
    }

    /**
     * 登录
     */
    @PostMapping(SIGN_IN_REQUEST_MAPPING)
    SignerProfileVO signIn(@RequestBody @Valid SignInput input) {
        return signService.signIn(input);
    }

    /**
     * 登出
     */
    @PostMapping(SIGN_OUT_REQUEST_MAPPING)
    void signOut() {
        signService.signOut();
    }

    /**
     * 当前调用者是否登录中
     */
    @GetMapping(IS_SIGNING_REQUEST_MAPPING)
    Boolean isSigning() {
        return Signer.isSigning();
    }

}