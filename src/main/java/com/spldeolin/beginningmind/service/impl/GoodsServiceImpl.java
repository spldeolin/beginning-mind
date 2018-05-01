package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.spldeolin.beginningmind.api.CommonServiceImpl;
import com.spldeolin.beginningmind.api.dto.Page;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.dao.GoodsMapper;
import com.spldeolin.beginningmind.model.Goods;
import com.spldeolin.beginningmind.service.GoodsService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

/**
 * “商品”业务实现
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Service
@Log4j2
public class GoodsServiceImpl extends CommonServiceImpl<Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Long createEX(Goods goods) {
        /* 业务校验 */
        super.create(goods);
        return goods.getId();
    }

    @Override
    public void updateEX(Goods goods) {
        if (!isExist(goods.getId())) {
            throw new ServiceException("商品不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(goods)) {
            throw new ServiceException("商品数据过时");
        }
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new ServiceException("商品不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<Goods> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的商品全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<Goods> page(Integer pageNo, Integer pageSize) {
        Condition condition = new Condition(Goods.class);
        condition.createCriteria()/* 添加条件 */;
        PageHelper.startPage(pageNo, pageSize);
        return Page.wrap(goodsMapper.selectBatchByCondition(condition));
    }

}