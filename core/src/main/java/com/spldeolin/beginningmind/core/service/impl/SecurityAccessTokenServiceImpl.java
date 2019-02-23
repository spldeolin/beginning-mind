package com.spldeolin.beginningmind.core.service.impl;

import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.core.common.*;
import com.spldeolin.beginningmind.core.entity.SecurityAccessTokenEntity;
import com.spldeolin.beginningmind.core.service.SecurityAccessTokenService;

/**
 * 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
 *
 * @author Deolin 2019-02-23
 */
@Service
public class SecurityAccessTokenServiceImpl extends CommonServiceImpl<SecurityAccessTokenEntity> implements SecurityAccessTokenService {

}