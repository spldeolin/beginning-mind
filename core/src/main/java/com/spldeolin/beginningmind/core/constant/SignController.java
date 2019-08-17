package com.spldeolin.beginningmind.core.constant;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.input.SignInput;
import com.spldeolin.beginningmind.core.security.util.SignContext;
import com.spldeolin.beginningmind.core.service.SignService;
import com.spldeolin.beginningmind.core.vo.CaptchaVO;
import com.spldeolin.beginningmind.core.vo.SignerProfileVO;

/**
 * 登录、登出、登录状态等
 *
 * @author Deolin 2018/05/26
 */
@RestController("a")
@RequestMapping("sign2")
@Validated
public class SignController {

    @Autowired
    private SignService signService;

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    CaptchaVO captcha() {
        return signService.captcha();
    }

    /**
     * 登录
     */
    @PostMapping("/in")
    SignerProfileVO signIn(@RequestBody @Valid SignInput input) {
        return signService.signIn(input);
    }

    /**
     * 登出
     */
    @PostMapping("/out")
    void signOut() {
        signService.signOut();
    }

    /**
     * 当前是否登录中
     */
    @GetMapping("/isSigning")
    Boolean isSigning() {
        return SignContext.isSigning();
    }

}