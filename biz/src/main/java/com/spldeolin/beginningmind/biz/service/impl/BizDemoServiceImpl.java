package com.spldeolin.beginningmind.biz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.biz.entity.BizDemoEntity;
import com.spldeolin.beginningmind.biz.dao.BizDemoDao;
import com.spldeolin.beginningmind.biz.service.BizDemoService;

/**
 * @author Deolin 2019-03-18
 */
@Service
public class BizDemoServiceImpl implements BizDemoService {

    @Autowired
    private BizDemoDao bizDemoDao;


    @Override
    public List<BizDemoEntity> all() {
        return bizDemoDao.listAll();
    }
}