package com.spldeolin.beginningmind;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.dao.GoodsMapper;
import com.spldeolin.beginningmind.model.Goods;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class CommonMapperTests {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 乐观锁测试：通用Mapper的@Version注解
     */
    @Test
    public void testVersion() {
        Goods user = Goods.builder().updatedAt(LocalDateTime.of(2018, 4, 30, 7, 18, 32)).id(1L).name("111").build();
        log.info(goodsMapper.updateByIdSelective(user) + "结果");
    }

    @Test
    public void deleteById() {
        Long id = 83L;
        goodsMapper.deleteById(id);
    }

    @Test
    public void deleteByIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(2L);
        ids.add(3L);
        ids.add(4L);
        goodsMapper.deleteBatchByIds(Strings.join(ids, ','));
    }

    @Test
    public void insert() {
        goodsMapper.insert(Goods.builder().name("汉字").build());
    }

    @Test
    public void insertBatch() {
        List<Goods> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(Goods.builder().name("汉字" + i).build());
        }
        goodsMapper.insertBatch(users);
    }

    @Test
    public void physicallyDelete() {
        Long id = 75L;
        goodsMapper.physicallyDelete(id);
    }

    @Test
    public void updateByIdSelective() {
        Goods goods = Goods.builder().id(74L).name("汉字，不能改了").build();
        goodsMapper.updateByIdSelective(goods);
    }

    @Test
    public void selectById() {
        Long id = 74L;
        log.info(goodsMapper.selectById(id));
    }

    @Test
    public void selectBatchByIds() {
        List<Long> ids = generateIdList();
        log.info(goodsMapper.selectBatchByIds(StringUtils.join(ids, ',')));
    }

    @Test
    public void selectAll() {
        log.info(goodsMapper.selectAll());
    }

    @Test
    public void selectBatchByModel() {
        log.info(goodsMapper.selectBatchByModel(Goods.builder().name("汉字").build()));
    }

    @Test
    public void selectBatchByCondition() {
        Condition condition = new Condition(Goods.class);
        condition.createCriteria().andEqualTo("name", "string").andEqualTo("isDeleted", true);
        condition.orderBy("insertedAt").desc();
        log.info(goodsMapper.selectBatchByCondition(condition));
    }

    @Test
    public void selectCountByModel() {
        log.info(goodsMapper.selectCountByModel(Goods.builder().name("汉字").build()));
    }

    @Test
    public void selectCountByCondition() {
        Condition condition = new Condition(Goods.class);
        condition.createCriteria().andEqualTo("name", "string");
        condition.orderBy("insertedAt").desc();
        log.info(goodsMapper.selectCountByCondition(condition));
    }

    private List<Long> generateIdList() {
        List<Long> ids = new ArrayList<>();
        ids.add(70L);
        ids.add(71L);
        ids.add(72L);
        ids.add(73L);
        ids.add(74L);
        return ids;
    }

}
