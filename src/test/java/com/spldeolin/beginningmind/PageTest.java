package com.spldeolin.beginningmind;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spldeolin.beginningmind.entity.UserEntity;
import com.spldeolin.beginningmind.mapper.UserMapper;
import com.spldeolin.beginningmind.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2019-03-31
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
public class PageTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void t31() {
        Page<UserEntity> pageParam = new Page<>(1, 2); // 当前页码，每页条数

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getMobile, "1");

        IPage<UserEntity> pageResult = userMapper.selectPage(pageParam, wrapper);
        log.info(JsonUtils.toJsonPrettily(pageResult));
    }

    @Test
    public void t42() {
        Page<UserEntity> pageParam = new Page<>(1, 2); // 当前页码，每页条数

        List<UserEntity> pageResult = userMapper.listAsPageByMobile(pageParam, "1");
        log.info(JsonUtils.toJsonPrettily(pageResult));
    }

}