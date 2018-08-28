package com.spldeolin.beginningmind.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.controller.annotation.Authorization;
import com.spldeolin.beginningmind.core.service.UserService;

/**
 * 登录管理
 *
 * @author Deolin 2018/05/26
 * @author DeolinEX 2018/08/25
 */
@RestController
//@RequestMapping("/signManagement")
@Validated
public class SignManagementController {

    @Autowired
    private UserService userService;

    /**
     * 指定用户是否登录中
     */
    @Authorization(display = "查看指定用户是否登录中", menuId = 1L)
    @GetMapping("/isSigning")
    Boolean isSign(@RequestParam Long userId) {
        return userService.isAccountSigning(userId);
    }

    /**
     * 将指定用户踢下线
     */
    @Authorization(display = "将指定用户踢下线", menuId = 1L)
    @PostMapping("/kill")
    void kill(@RequestParam Long userId) {
        userService.killSigner(userId);
    }

}