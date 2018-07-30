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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.api.dto.PageParam;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.input.SignInput;
import com.spldeolin.beginningmind.core.model.SecurityRole;
import com.spldeolin.beginningmind.core.service.SignService;
import com.spldeolin.beginningmind.core.vo.SignerProfileVO;
import lombok.Data;

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

    @GetMapping("/exception")
    void ln68() {
        throw new ServiceException("sdfsdf");
    }

    @GetMapping("/n")
    Object ln74(PageParam pageParam, Integer i) {
        return SecurityRole.builder().name("测试").build();
    }

    @PostMapping("/body")
    void ln86(@RequestBody DTO one, DTO two, @RequestParam("aaa") String aaa) {
        System.out.println(one);
    }

    @Data
    public static class DTO {

        private String name;
        private Integer age;
    }

}