package com.spldeolin.beginningmind.launch.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spldeolin.beginningmind.core.dao.UserMapper;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.util.Jsons;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2019-03-31
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class PageTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void t31() {
        Page<UserEntity> pageParam = new Page<>(1, 2); // 当前页码，每页条数

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getMobile, "1");

        IPage<UserEntity> pageResult = userMapper.selectPage(pageParam, wrapper);
        log.info(Jsons.beautify(pageResult));
    }

    @Test
    public void t42() {
        Page<UserEntity> pageParam = new Page<>(1, 2); // 当前页码，每页条数

        IPage<UserEntity> pageResult = userMapper.searchAsPageByMobile(pageParam, "1");
        log.info(Jsons.beautify(pageResult));
    }

}