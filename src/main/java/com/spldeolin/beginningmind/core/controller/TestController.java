package com.spldeolin.beginningmind.core.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.Requests;

/**
 * @author Deolin 2018/11/16
 */

@RestController
@RequestMapping("/test")
@Validated
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/a")
    Object ln17() {
        return Integer.valueOf("a");
    }

    @PostMapping("/s")
    List<User> ln23(User user) {
        return userService.listAll();
    }

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/i18n")
    Object i18n() {
        return messageSource.getMessage("hello", new String[]{"Deolin"}, Requests.request().getLocale());
    }

}