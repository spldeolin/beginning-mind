package com.spldeolin.beginningmind.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.input.SignInput;
import com.spldeolin.beginningmind.security.util.SignContext;
import com.spldeolin.beginningmind.service.SignService;
import com.spldeolin.beginningmind.vo.SignerProfileVO;

/**
 * 登录、登出、登录状态等
 *
 * @author Deolin 2018/05/26
 */
@RestController
@RequestMapping("sign")
@Validated
public class SignController {

    @Autowired
    private SignService signService;

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