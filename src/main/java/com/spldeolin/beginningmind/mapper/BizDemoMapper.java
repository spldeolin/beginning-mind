package com.spldeolin.beginningmind.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.MapKey;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spldeolin.beginningmind.entity.BizDemoEntity;

/**
 * 业务示例
 *
 * @author Deolin 2019-06-08
 */
public interface BizDemoMapper extends BaseMapper<BizDemoEntity> {

    @MapKey("id")
    Map<Long, BizDemoEntity> listAllAsMap();

}