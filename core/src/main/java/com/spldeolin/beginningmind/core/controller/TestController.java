package com.spldeolin.beginningmind.core.controller;

import java.util.List;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.repository.UserRepo;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.util.MdcRunnable;
import com.spldeolin.beginningmind.core.util.Sessions;
import com.spldeolin.beginningmind.core.util.WebContext;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/16
 */
@RestController
@RequestMapping("/test")
@Log4j2
public class TestController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/a")
    Object ln17() {
        return Integer.valueOf("a");
    }

    @PostMapping("/s")
    List<UserEntity> ln23(UserEntity user) {
        return userRepo.listAll();
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

    @PostMapping("/post")
    String post(@RequestBody UserEntity user) {
        return "SUCEESS" + user;
    }

//    @GetMapping("/page")
//    IPage<UserEntity> page(Page page) {
//        LambdaQueryWrapper<UserEntity> query = new LambdaQueryWrapper<>();
//        query.le(UserEntity::getMobile, "999");
//        return userRepo.page(page, query);
//    }
//
//    @PostMapping("/requestTrackReport")
//    @SecurityAccess(AccessMode.TOKEN)
//    Map<Integer, Object> requestTrackReport(@RequestBody User2permissionEntity user2permission) {
//        log.info(user2permission);
//        Map<Integer, Object> result = Maps.newHashMap();
//
//        LambdaQueryWrapper<UserEntity> query = new LambdaQueryWrapper<>();
//        query.gt(UserEntity::getMobile, 4);
//        result.put(1, userRepo.searchBatch(query));
//
//        query = new LambdaQueryWrapper<>();
//        query.ge(UserEntity::getMobile, 3);
//        result.put(1, userRepo.searchBatch(query));
//
//        query = new LambdaQueryWrapper<>();
//        query.lt(UserEntity::getMobile, 2);
//        result.put(2, userRepo.searchBatch(query));
//
//        query = new LambdaQueryWrapper<>();
//        query.le(UserEntity::getMobile, "999");
//        result.put(3, userRepo.searchBatch(query));
//
//        query = new LambdaQueryWrapper<>();
//        query.eq(UserEntity::getEnableSign, false);
//        result.put(4, userRepo.searchBatch(query));
//
//        query = new LambdaQueryWrapper<>();
//        query.in(UserEntity::getPassword, Lists.newArrayList("abc_111", "12312_de", "")).eq(UserEntity::getName, "汉字");
//        result.put(5, userRepo.searchBatch(query));
//
//        query = new LambdaQueryWrapper<>();
//        query.ne(UserEntity::getName, "随意").or().eq(UserEntity::getName, "随意");
//        result.put(6, userRepo.searchBatch(query));
//
//        query = new LambdaQueryWrapper<>();
//        query.orderByDesc(UserEntity::getId); // 暂时无法消除这个警告
//        result.put(7, userRepo.searchBatch(query));
//
//        query = new LambdaQueryWrapper<>();
//        query.select(UserEntity::getId, UserEntity::getName);
//        result.put(8, userRepo.searchBatch(query));
//
//        query = new LambdaQueryWrapper<>();
//        query.like(UserEntity::getName, "a");
//        result.put(9, userRepo.searchBatch(query));
//
//        query = new LambdaQueryWrapper<>();
//        query.likeRight(UserEntity::getName, "D");
//        result.put(10, userRepo.searchBatch(query));
//
//        return result;
//    }

    @GetMapping("/asyncMdc")
    Object asyncMdc() {
        log.info("sync");
        log.info("sync");
        log.info("sync");
        log.info("sync");

        Executors.newFixedThreadPool(1).submit(new MdcRunnable(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException ignored) {
            }
            log.info("async");
        }));

        log.info("sync");

        return "SUCCESS";
    }


}