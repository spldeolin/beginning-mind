package com.spldeolin.beginningmind.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/12/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class QueryWrapperLambdaTest {

    @Autowired
    private UserService userService;

    @Test
    @SneakyThrows
    public void t() {
        // WHERE is_deleted=false AND mobile > ?
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.gt(User::getMobile, 4);
        userService.searchBatch(query).forEach(log::info);

        // WHERE is_deleted=false AND mobile >= ?
        query = new LambdaQueryWrapper<>();
        query.ge(User::getMobile, 3);
        userService.searchBatch(query).forEach(log::info);

        // WHERE is_deleted=false AND mobile < ?
        query = new LambdaQueryWrapper<>();
        query.lt(User::getMobile, 2);
        userService.searchBatch(query).forEach(log::info);

        // WHERE is_deleted=false AND mobile <= ?
        query = new LambdaQueryWrapper<>();
        query.le(User::getMobile, "999");
        userService.searchBatch(query).forEach(log::info);

        // WHERE is_deleted=false AND enable_sign = ?
        query = new LambdaQueryWrapper<>();
        query.eq(User::getEnableSign, false);
        userService.searchBatch(query).forEach(log::info);

        // WHERE is_deleted=false AND password IN (?,?,?) AND name = ?
        query = new LambdaQueryWrapper<>();
        query.in(User::getPassword, Lists.newArrayList("abc_111", "12312_de", "")).eq(User::getName, "汉字");
        userService.searchBatch(query).forEach(log::info);

        // WHERE is_deleted=false AND name <> ? OR name = ?
        query = new LambdaQueryWrapper<>();
        query.ne(User::getName, "随意").or().eq(User::getName, "随意");
        userService.searchBatch(query).forEach(log::info);

    }

}