package com.spldeolin.beginningmind.core.controller;

import java.util.List;
import java.util.Map;
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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.model.User2permission;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.Sessions;
import com.spldeolin.beginningmind.core.util.WebContext;
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
        return messageSource.getMessage("hello", new String[]{"Deolin"}, WebContext.getRequest().getLocale());
    }

    @GetMapping("/setSes")
    Object ln46() {
        Sessions.set("one-cookie", "内部号");
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

    @GetMapping("/page")
    IPage<User> page(Page page) {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.le(User::getMobile, "999");
        return userService.page(page, query);
    }

    @PostMapping("/requestTrackReport")
    Map<Integer, Object> requestTrackReport(@RequestBody User2permission user2permission) {
        log.info(user2permission);
        Map<Integer, Object> result = Maps.newHashMap();

        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.gt(User::getMobile, 4);
        result.put(1, userService.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.ge(User::getMobile, 3);
        result.put(1, userService.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.lt(User::getMobile, 2);
        result.put(2, userService.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.le(User::getMobile, "999");
        result.put(3, userService.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.eq(User::getEnableSign, false);
        result.put(4, userService.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.in(User::getPassword, Lists.newArrayList("abc_111", "12312_de", "")).eq(User::getName, "汉字");
        result.put(5, userService.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.ne(User::getName, "随意").or().eq(User::getName, "随意");
        result.put(6, userService.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.orderByDesc(User::getId); // 暂时无法消除这个警告
        result.put(7, userService.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.select(User::getId, User::getName);
        result.put(8, userService.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.like(User::getName, "a");
        result.put(9, userService.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.likeRight(User::getName, "D");
        result.put(10, userService.searchBatch(query));

        return result;
    }

}