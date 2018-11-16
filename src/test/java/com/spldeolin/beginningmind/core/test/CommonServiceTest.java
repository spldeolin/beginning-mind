package com.spldeolin.beginningmind.core.test;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.Times;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/12
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class CommonServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void create() {
        User user = new User();
        user.setInsertedAt(LocalDateTime.of(2000, 11, 12, 13, 14, 15)); // CommonFieldFillHandler填充时将会覆盖这个值
        user.setName("汉字");
        user.setMobile("1");
        userService.create(user);

        log.info(user.getId());
        log.info("结束");
    }

    @Test
    public void createBatch() {
        List<User> users = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setInsertedAt(LocalDateTime.of(2000, 11, 12, 13, 14, 15));
            user.setName("批量" + i);
            user.setMobile(String.valueOf(i));
            users.add(user);
        }
        userService.create(users);

        users.forEach(user -> log.info(user.getId()));
    }

    @Test
    public void get() {
        log.info(userService.get(0L));
        log.info(userService.get(309844791922689L));
    }

    @Test
    public void list() {
        userService.list(Lists.newArrayList(309844787728385L, 303270472060928L, 0L, 290956356227072L))
                .forEach(log::info);

        log.info(userService.list(Lists.newArrayList(9L, 10L, 0L, 11L)).size());
    }

    @Test
    public void map() {
        log.info(userService.map(Lists.newArrayList(309844787728385L, 303270472060928L, 0L, 290956356227072L)));
        ;

        log.info(userService.list(Lists.newArrayList(9L, 10L, 0L, 11L)).size());
    }

    @Test
    public void listAll() {
        userService.listAll().forEach(log::info);
    }

    @Test
    public void mapAll() {
        log.info(userService.mapAll());
    }

    @Test
    public void update() {
        User user = new User();
        user.setId(290956356227072L);
        try {
            userService.update(user);  // 只有id不为null时baseMapper会抛出BadSqlGrammarException异常
        } catch (Exception e) {
            log.error("", e);
        }

        user = new User();
        user.setName("阿斯顿");
        log.info(userService.update(user)); // id为null时返回false

        user = new User();
        user.setId(0L);
        user.setName("阿斯顿");
        log.info(userService.update(user)); // id对应数据不存在时返回false
    }

    @Test
    public void delete() {
        userService.delete(303270472060928L);
    }

    @Test
    public void deleteBatch() {
        List<Long> ids = Lists
                .newArrayList(367534259965952L, 367534452903936L, 367534452903937L, 367534457098240L, 367534457098241L);
        userService.delete(ids);
    }

    @Test
    public void searchOne() {
        User user = new User();
        user.setName("汉字");
        user.setEmail("1");
        log.info(userService.searchOne(user));
    }

    @Test
    public void searchOne2() {
        log.info(userService.searchOne("email", "1"));
    }

    @Test
    public void searchBatch() {
        User user = new User();
        user.setName("汉字");
        userService.searchBatch(user).forEach(log::info);
    }

    @Test
    public void searchBatch2() {
        userService.searchBatch("email", "2").forEach(log::info);
    }

    @Test
    public void queryWrapper() {
        userService
                .searchBatch(new QueryWrapper<User>().lt("inserted_at", Times.toLocalDateTime("2018-11-13 08:35:08")))
                .forEach(log::info);
    }

    @Test
    public void count() {
        User user = new User();
        user.setName("汉字");
        log.info(userService.count(user));
    }

}