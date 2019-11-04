package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.repository.BizDemoRepo;
import com.spldeolin.beginningmind.entity.BizDemoEntity;
import com.spldeolin.beginningmind.service.BizDemoService;

/**
 * @author Deolin 2019-03-18
 */
@Service
public class BizDemoServiceImpl implements BizDemoService {

    @Autowired
    private BizDemoRepo bizDemoRepo;


    @Override
    public List<BizDemoEntity> all() {
        return bizDemoRepo.listAll();
    }
}