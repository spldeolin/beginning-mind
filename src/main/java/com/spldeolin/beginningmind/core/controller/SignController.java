/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.core.controller;

import javax.validation.Valid;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.input.SignInput;
import com.spldeolin.beginningmind.core.service.SignService;
import com.spldeolin.beginningmind.core.vo.SignerProfileVO;

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
    String captcha() {
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
     * 当前调用者是否登录中
     */
    @GetMapping("/isSigning")
    Boolean isSign() {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated() || subject.isRemembered();
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/t72")
    Object t72() {
        redisTemplate.opsForValue().set("aaa", SignerProfileVO.builder().username("a啊").build());
        return "SUCCESS";
    }

    @GetMapping("/t78")
    Object t78() {
        return redisTemplate.opsForValue().get("aaa");
    }

}