package com.spldeolin.beginningmind.service;

import java.util.List;
import com.spldeolin.beginningmind.entity.SecurityAccessTokenEntity;

/**
 * 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
 *
 * @author Deolin 2019-02-23
 */
public interface SecurityAccessTokenService {

    List<SecurityAccessTokenEntity> listAll();

}