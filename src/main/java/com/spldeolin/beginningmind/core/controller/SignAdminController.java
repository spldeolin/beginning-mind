/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.controller.annotation.Permission;
import com.spldeolin.beginningmind.core.service.SecurityUserService;

/**
 * 登录管理
 *
 * @author Deolin 2018/05/26
 */
@RestController
@RequestMapping("/signAdmin")
@Validated
public class SignAdminController {

    @Autowired
    private SecurityUserService securityAccountService;

    /**
     * 指定用户是否登录中
     */
    @Permission(displayName = "查看指定用户是否登录中")
    @GetMapping("/isSigning")
    Boolean isSign(@RequestParam Long userId) {
        return securityAccountService.isAccountSigning(userId);
    }

    /**
     * 将指定用户踢下线
     */
    @Permission(displayName = "将指定用户踢下线")
    @PostMapping("/kill")
    void kill(@RequestParam Long userId) {
        securityAccountService.killSigner(userId);
    }

}