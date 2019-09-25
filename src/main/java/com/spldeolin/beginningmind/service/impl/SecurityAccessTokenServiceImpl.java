package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.dao.SecurityAccessTokenDao;
import com.spldeolin.beginningmind.entity.SecurityAccessTokenEntity;
import com.spldeolin.beginningmind.service.SecurityAccessTokenService;

/**
 * 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
 *
 * @author Deolin 2019-02-23
 */
@Service
public class SecurityAccessTokenServiceImpl implements SecurityAccessTokenService {

    @Autowired
    private SecurityAccessTokenDao securityAccessTokenDao;

    @Override
    public List<SecurityAccessTokenEntity> listAll() {
        return securityAccessTokenDao.listAll();
    }

}