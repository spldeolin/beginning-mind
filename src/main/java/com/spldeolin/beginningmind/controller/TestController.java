package com.spldeolin.beginningmind.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.entity.User2permissionEntity;
import com.spldeolin.beginningmind.entity.UserEntity;
import com.spldeolin.beginningmind.mapper.UserMapper;
import com.spldeolin.beginningmind.security.annotation.SecurityAccess;
import com.spldeolin.beginningmind.security.annotation.SecurityAccess.AccessMode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2018/11/16
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/elementsNonNullDemo")
    Collection<Long> elementsNonNullDemo(@RequestBody @NotNull List<@NotNull @Max(11L) Long> aaa) {
        return aaa;
    }

    @PostMapping("/requestTrackReport")
    @SecurityAccess(AccessMode.TOKEN)
    Map<Integer, Object> requestTrackReport(@RequestBody User2permissionEntity user2permission) {
        log.info("user2permission={}", user2permission);
        Map<Integer, Object> result = Maps.newHashMap();

        LambdaQueryWrapper<UserEntity> query = new LambdaQueryWrapper<>();
        query.gt(UserEntity::getMobile, 4);
        result.put(1, userMapper.selectList(query));

        query = new LambdaQueryWrapper<>();
        query.ge(UserEntity::getMobile, 3);
        result.put(1, userMapper.selectList(query));

        query = new LambdaQueryWrapper<>();
        query.lt(UserEntity::getMobile, 2);
        result.put(2, userMapper.selectList(query));

        query = new LambdaQueryWrapper<>();
        query.le(UserEntity::getMobile, "999");
        result.put(3, userMapper.selectList(query));

        query = new LambdaQueryWrapper<>();
        query.eq(UserEntity::getEnableSign, false);
        result.put(4, userMapper.selectList(query));

        query = new LambdaQueryWrapper<>();
        query.in(UserEntity::getPassword, Lists.newArrayList("abc_111", "12312_de", "")).eq(UserEntity::getName, "汉字");
        result.put(5, userMapper.selectList(query));

        query = new LambdaQueryWrapper<>();
        query.ne(UserEntity::getName, "随意").or().eq(UserEntity::getName, "随意");
        result.put(6, userMapper.selectList(query));

        query = new LambdaQueryWrapper<>();
        query.orderByDesc(UserEntity::getId); // 暂时无法消除这个警告
        result.put(7, userMapper.selectList(query));

        query = new LambdaQueryWrapper<>();
        query.select(UserEntity::getId, UserEntity::getName);
        result.put(8, userMapper.selectList(query));

        query = new LambdaQueryWrapper<>();
        query.like(UserEntity::getName, "a");
        result.put(9, userMapper.selectList(query));

        query = new LambdaQueryWrapper<>();
        query.likeRight(UserEntity::getName, "D");
        result.put(10, userMapper.selectList(query));

        return result;
    }

}