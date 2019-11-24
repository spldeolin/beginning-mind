package com.spldeolin.beginningmind.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.MapKey;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spldeolin.beginningmind.entity.BizDemoEntity;
import com.spldeolin.beginningmind.entity.SecurityAccessTokenEntity;

/**
 * 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
 *
 * @author Deolin 2019-02-23
 */
public interface SecurityAccessTokenMapper extends BaseMapper<SecurityAccessTokenEntity> {

    List<SecurityAccessTokenEntity> listAll();

}