package com.spldeolin.beginningmind.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.spldeolin.beginningmind.core.api.CommonServiceImpl;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.dao.BuyerMapper;
import com.spldeolin.beginningmind.core.model.Buyer;
import com.spldeolin.beginningmind.core.service.BuyerService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

/**
 * “买家”业务实现
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Service
@Log4j2
public class BuyerServiceImpl extends CommonServiceImpl<Buyer> implements BuyerService {

    @Autowired
    private BuyerMapper buyerMapper;

    @Override
    public Long createEX(Buyer buyer) {
        /* 业务校验 */
        super.create(buyer);
        return buyer.getId();
    }

    @Override
    public void updateEX(Buyer buyer) {
        if (!isExist(buyer.getId())) {
            throw new ServiceException("买家不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(buyer)) {
            throw new ServiceException("买家数据过时");
        }
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new ServiceException("买家不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<Buyer> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的买家全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<Buyer> page(Integer pageNo, Integer pageSize) {
        Condition condition = new Condition(Buyer.class);
        condition.createCriteria()/* 添加条件 */;
        PageHelper.startPage(pageNo, pageSize);
        return Page.wrap(buyerMapper.selectBatchByCondition(condition));
    }

}