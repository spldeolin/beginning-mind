package com.spldeolin.beginningmind.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.spldeolin.beginningmind.entity.BizDemoEntity;
import com.spldeolin.beginningmind.mapper.BizDemoMapper;
import com.spldeolin.beginningmind.service.BizDemoService;

/**
 * @author Deolin 2019-03-18
 */
@Service
@Validated
public class BizDemoServiceImpl implements BizDemoService {

    @Autowired
    private BizDemoMapper bizDemoMapper;

    @Override
    public Map<Long, BizDemoEntity> all() {
        return bizDemoMapper.listAllAsMap();
    }

    @Override
    public void doSomething(BizDemoEntity bizDemoEntity, Long id) {
        System.out.println(Integer.valueOf("a"));
    }

}