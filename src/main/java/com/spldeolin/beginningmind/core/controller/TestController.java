package com.spldeolin.beginningmind.core.controller;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spldeolin.beginningmind.core.dao.UserMapper;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.Requests;
import com.spldeolin.beginningmind.core.util.Sessions;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/16
 */

@RestController
@RequestMapping("/test")
@Validated
@Log4j2
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

    @GetMapping("/setSes")
    Object ln46() {
        Sessions.set("one-cookie", "内部号", 10);
        return null;
    }

    @GetMapping("/getSes")
    Object ln54() {
        return Sessions.get("one-cookie");
    }

    @Autowired
    @Qualifier("anonUrlsPrefix")
    private Set<String> anonUrlsPrefix;


    @Autowired
    @Qualifier("authUrls")
    private Set<String> authUrls;

    @GetMapping("/qualifier")
    Object qualifier() {
        log.info(authUrls);
        return anonUrlsPrefix;
    }

    @PostMapping("/post")
    String post(@RequestBody User user) {
        return "SUCEESS" + user;
    }

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/page")
    Page<User> page(Page<User> page) {

        return page.setRecords(userMapper.pages(page));
    }

}