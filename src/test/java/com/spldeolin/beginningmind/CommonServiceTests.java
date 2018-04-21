package com.spldeolin.beginningmind;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.model.User;
import com.spldeolin.cadeau.library.inherited.CommonService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class CommonServiceTests {

    @Autowired
    private CommonService<User> service;

    /**
     * 创建一个资源
     */
    @Test
    public void create() {
        service.create(User.builder().name("业务层操作").build());
    }

    /**
     * 创建一批资源
     */
    @Test
    public void createB() {
        List<User> users = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            users.add(User.builder().name("业务层操作" + i).hms(LocalTime.now()).build());
        }
        service.create(users);
    }

    /**
     * 获取一个资源
     */
    @Test
    public void get() {
        log.info(service.get(74L).orElse(null));
        log.info(service.get(73L).orElse(null));
    }

    /**
     * 获取多个资源
     */
    @Test
    public void getB() {
        log.info(service.get(Lists.newArrayList(73L, 74L, 75L)));
    }

    /**
     * 获取全部资源
     */
    @Test
    public void listAll() {
        log.info(service.listAll());
    }

    /**
     * 更新一个资源，本方法不校验资源是否存在
     */
    @Test
    public void update() {
        User user = User.builder().updatedAt(LocalDateTime.of(2018, 4, 15, 19, 49, 6)).id(73L).name("111").build();
        log.info(service.update(user) ? "用户更新成功" : "更新失败，可能的原因是用户不存在、已被删除或是本次请求数据过时");
    }

    /**
     * 删除一个资源，本方法不校验资源是否存在
     */
    @Test
    public void delete() {
        log.info(service.delete(83L));
        log.info(service.delete(83L));
    }

    /**
     * 删除多个资源，本方法不校验资源是否存在
     */
    @Test
    public void deleteB() {
        log.info(service.delete(Lists.newArrayList(83L, 72L)));
    }

    /**
     * 物理删除一个资源（资源将会彻底消失）
     */
    @Test
    public void physicallyDelete() {
        log.info(service.physicallyDelete(88L));
        log.info(service.physicallyDelete(88L));
    }

    /**
     * 测试batchToOne私有方法
     */
    @Test
    public void batchToOne() {
        Condition condition = new Condition(User.class);
        condition.createCriteria().andEqualTo("name", "汉字");
        log.info(service.searchBatch(condition));

        User user = User.builder().name("汉字").build();
        try {
            service.searchOne(user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
