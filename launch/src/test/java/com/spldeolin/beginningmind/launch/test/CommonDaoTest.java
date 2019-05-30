package com.spldeolin.beginningmind.launch.test;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.dao.UserDao;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class CommonDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void create() {
        UserEntity user = new UserEntity();
        user.setInsertedAt(LocalDateTime.of(2000, 11, 12, 13, 14, 15)); // CommonFieldFillHandler填充时将会覆盖这个值
        user.setName("汉字");
        user.setMobile("1");
        userDao.create(user);

        log.info(user.getId());
        log.info("结束");
    }

    @Test
    public void createBatch() {
        List<UserEntity> users = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            UserEntity user = new UserEntity();
            user.setInsertedAt(LocalDateTime.of(2000, 11, 12, 13, 14, 15));
            user.setName("批量" + i);
            user.setMobile(String.valueOf(i));
            users.add(user);
        }
        userDao.create(users);

        users.forEach(user -> log.info(user.getId()));
    }

    @Test
    public void get() {
        log.info(userDao.get(0L));
        log.info(userDao.get(309844791922689L));
    }

    @Test
    public void list() {
        userDao.list(Lists.newArrayList(309844787728385L, 303270472060928L, 0L, 290956356227072L))
                .forEach(log::info);

        log.info(userDao.list(Lists.newArrayList(9L, 10L, 0L, 11L)).size());
    }

    @Test
    public void listAll() {
        userDao.listAll().forEach(log::info);
    }

    @Test
    public void update() {
        UserEntity user = new UserEntity();
        user.setId(290956356227072L);
        try {
            userDao.update(user);  // 只有id不为null时baseMapper会抛出BadSqlGrammarException异常
        } catch (Exception e) {
            log.error("", e);
        }

        user = new UserEntity();
        user.setName("阿斯顿");
        log.info(userDao.update(user)); // id为null时返回false

        user = new UserEntity();
        user.setId(0L);
        user.setName("阿斯顿");
        log.info(userDao.update(user)); // id对应数据不存在时返回false
    }

    @Test
    public void updateBatch() throws Exception {
        UserEntity set = new UserEntity();
        set.setSerialNumber(RandomStringUtils.randomAlphabetic(2));

        LambdaQueryWrapper<UserEntity> where = new LambdaQueryWrapper<>();
        where.like(UserEntity::getName, "批量");

//        userDao.update(set, where);
    }

    @Test
    public void delete() {
        userDao.delete(400214720647168L);
    }

    @Test
    public void deleteBatch() {
        List<Long> ids = Lists.newArrayList(36286656290099200L, 36286656566923264L, 36286656571117568L);
        userDao.delete(ids);
    }

    @Test
    public void searchOne() {
        UserEntity user = new UserEntity();
        user.setName("汉字");
        user.setEmail("1");
//        log.info(userDao.searchOne(user));
    }

    @Test
    public void searchOne2() {
//        log.info(userDao.searchOne(UserEntity::getEmail, "1"));
    }

    @Test
    public void searchBatch() {
        UserEntity user = new UserEntity();
        user.setName("汉字");
//        userDao.searchBatch(user).forEach(log::info);
    }

    @Test
    public void searchBatch2() {
//        userDao.searchBatch(UserEntity::getEmail, "2").forEach(log::info);
    }

    @Test
    public void queryWrapper() {
//        userDao
//                .searchBatch(
//                        new QueryWrapper<UserEntity>().lt("inserted_at", Times.toLocalDateTime("2018-11-13 08:35:08")))
//                .forEach(log::info);
    }

    @Test
    public void count() {
        UserEntity user = new UserEntity();
        user.setName("汉字");
        log.info(userDao.count(user));
    }

}