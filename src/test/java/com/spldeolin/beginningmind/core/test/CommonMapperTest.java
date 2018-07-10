/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.dao.SecurityUserMapper;
import com.spldeolin.beginningmind.core.model.SecurityUser;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

/**
 * @author Deolin 2018/06/09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class CommonMapperTest {

    @Autowired
    private SecurityUserMapper securityUserMapper;

    @Test
    public void testDelete() {
        securityUserMapper.deleteById(1L);
        securityUserMapper.deleteBatchByIds("2, 3, 4");
    }

    @Test
    public void testInsert() {
        securityUserMapper.insertBatch(Lists.newArrayList(
                SecurityUser.builder().nickname("1adkljads").deletionFlag(110086L).build(),
                SecurityUser.builder().nickname("2adkljads").deletionFlag(210086L).build(),
                SecurityUser.builder().nickname("3adkljads").deletionFlag(310086L).build(),
                SecurityUser.builder().nickname("4adkljads").deletionFlag(410086L).build(),
                SecurityUser.builder().nickname("5adkljads").deletionFlag(510086L).build()
        ));
        SecurityUser user = SecurityUser.builder().nickname("adkljads").deletionFlag(10086L).build();
        securityUserMapper.insert(user);
        log.info(user.getId());
    }

    @Test
    public void testUpdate() {
        SecurityUser user = SecurityUser.builder().id(18L).nickname("汉字").deletionFlag(5464646464L).build();
        log.info(securityUserMapper.updateByIdSelective(user));
    }

    @Test
    public void testSelect() {
        log.info("selectBatchByIds");
        securityUserMapper.selectBatchByIds("1,6").forEach(log::info);
        log.info("selectAll");
        securityUserMapper.selectAll().forEach(log::info);
        log.info("selectBatchByModel");
        securityUserMapper.selectBatchByModel(SecurityUser.builder().nickname("汉字").build()).forEach(log::info);
        log.info("selectCountByModel");
        log.info(securityUserMapper.selectCountByModel(SecurityUser.builder().enableSign(true).build()));
        log.info("selectById");
        log.info(securityUserMapper.selectById(2L));

        Condition condition = new Condition(SecurityUser.class);
        condition.createCriteria().andEqualTo("nickname", "1adkljads");
        log.info("selectBatchByCondition");
        securityUserMapper.selectBatchByCondition(condition).forEach(log::info);
        log.info("selectCountByCondition");
        log.info(securityUserMapper.selectCountByCondition(condition));
    }

}