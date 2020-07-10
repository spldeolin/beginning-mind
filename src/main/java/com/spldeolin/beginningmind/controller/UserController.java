package com.spldeolin.beginningmind.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.input.UserInput;
import com.spldeolin.beginningmind.service.UserService;

/**
 * “用户”管理
 *
 * @author Deolin 2018/8/4
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 创建一个“用户”
     *
     * @param userInput 待创建的“用户”
     * @return 创建成功后生成的ID
     */
    @PostMapping("/create")
    Long create(@RequestBody @Valid UserInput userInput) {
        return userService.createUser(userInput.toEntity());
    }

}