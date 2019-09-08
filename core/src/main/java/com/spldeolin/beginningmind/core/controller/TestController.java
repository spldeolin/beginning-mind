package com.spldeolin.beginningmind.core.controller;

import java.util.Map;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.core.dao.UserDao;
import com.spldeolin.beginningmind.core.entity.User2permissionEntity;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.security.annotation.SecurityAccess;
import com.spldeolin.beginningmind.core.security.annotation.SecurityAccess.AccessMode;
import com.spldeolin.beginningmind.core.util.MdcRunnable;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/16
 */
@RestController
@RequestMapping("/test")
@Log4j2
public class TestController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/requestTrackReport")
    @SecurityAccess(AccessMode.TOKEN)
    Map<Integer, Object> requestTrackReport(@RequestBody User2permissionEntity user2permission) {
        log.info(user2permission);
        Map<Integer, Object> result = Maps.newHashMap();

        LambdaQueryWrapper<UserEntity> query = new LambdaQueryWrapper<>();
        query.gt(UserEntity::getMobile, 4);
        result.put(1, userDao.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.ge(UserEntity::getMobile, 3);
        result.put(1, userDao.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.lt(UserEntity::getMobile, 2);
        result.put(2, userDao.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.le(UserEntity::getMobile, "999");
        result.put(3, userDao.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.eq(UserEntity::getEnableSign, false);
        result.put(4, userDao.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.in(UserEntity::getPassword, Lists.newArrayList("abc_111", "12312_de", "")).eq(UserEntity::getName, "汉字");
        result.put(5, userDao.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.ne(UserEntity::getName, "随意").or().eq(UserEntity::getName, "随意");
        result.put(6, userDao.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.orderByDesc(UserEntity::getId); // 暂时无法消除这个警告
        result.put(7, userDao.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.select(UserEntity::getId, UserEntity::getName);
        result.put(8, userDao.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.like(UserEntity::getName, "a");
        result.put(9, userDao.searchBatch(query));

        query = new LambdaQueryWrapper<>();
        query.likeRight(UserEntity::getName, "D");
        result.put(10, userDao.searchBatch(query));

        return result;
    }

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