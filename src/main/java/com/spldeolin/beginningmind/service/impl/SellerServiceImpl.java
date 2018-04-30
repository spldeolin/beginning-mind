package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.spldeolin.beginningmind.api.CommonServiceImpl;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.dao.SellerMapper;
import com.spldeolin.beginningmind.dto.Page;
import com.spldeolin.beginningmind.model.Seller;
import com.spldeolin.beginningmind.service.SellerService;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

/**
 * “卖家”业务实现
 *
 * @author Deolin 2018/4/30
 * @generator Cadeau Support
 */
@Service
@Log4j2
public class SellerServiceImpl extends CommonServiceImpl<Seller> implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public Long createEX(Seller seller) {
        /* 业务校验 */
        super.create(seller);
        return seller.getId();
    }

    @Override
    public void updateEX(Seller seller) {
        if (!isExist(seller.getId())) {
            throw new ServiceException("卖家不存在或是已被删除");
        }
        /* 业务校验 */
        if (!super.update(seller)) {
            throw new ServiceException("卖家数据过时");
        }
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new ServiceException("卖家不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<Seller> exist = super.get(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的卖家全部不存在或是已被删除");
        }
        /* 业务校验 */
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Page<Seller> page(Integer pageNo, Integer pageSize) {
        Condition condition = new Condition(Seller.class);
        condition.createCriteria()/* 添加条件 */;
        PageHelper.startPage(pageNo, pageSize);
        return Page.wrap(sellerMapper.selectBatchByCondition(condition));
    }

}