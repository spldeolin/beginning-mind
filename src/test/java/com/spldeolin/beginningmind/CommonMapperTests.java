package com.spldeolin.beginningmind;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.dao.UserMapper;
import com.spldeolin.beginningmind.model.User;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class CommonMapperTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 乐观锁测试：通用Mapper的@Version注解
     */
    @Test
    public void testVersion() {
        User user = User.builder().updatedAt(null).id(73L).name("111").build();
        log.info(userMapper.updateByIdSelective(user) + "结果");
    }

    @Test
    public void deleteById() {
        Long id = 1L;
        userMapper.deleteById(id);
    }

    @Test
    public void deleteByIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(2L);
        ids.add(3L);
        ids.add(4L);
        userMapper.deleteBatchByIds(Strings.join(ids, ','));
    }

    @Test
    public void insert() {
        userMapper.insert(User.builder().name("汉字").hms(LocalTime.now()).build());
    }

    @Test
    public void insertBatch() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(User.builder().name("汉字" + i).ymd(LocalDate.now().plus(i, ChronoUnit.DAYS)).build());
        }
        userMapper.insertBatch(users);
    }

    @Test
    public void physicallyDelete() {
        Long id = 75L;
        userMapper.physicallyDelete(id);
    }

    @Test
    public void updateByIdSelective() {
        User user = User.builder().id(74L).name("汉字，不能改了").build();
        userMapper.updateByIdSelective(user);
    }

}
