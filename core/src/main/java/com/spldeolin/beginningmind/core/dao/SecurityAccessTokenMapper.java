package com.spldeolin.beginningmind.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spldeolin.beginningmind.core.entity.SecurityAccessTokenEntity;

/**
 * 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
 *
 * @author Deolin 2019-02-23
 */
public interface SecurityAccessTokenMapper extends BaseMapper<SecurityAccessTokenEntity> {

}