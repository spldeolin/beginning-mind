package com.spldeolin.beginningmind.service;

import java.util.Map;
import com.spldeolin.beginningmind.entity.BizDemoEntity;

/**
 * @author Deolin 2019-03-18
 */
public interface BizDemoService {

    Map<Long, BizDemoEntity> all();

}