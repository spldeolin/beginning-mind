package com.spldeolin.beginningmind.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.entity.PermissionEntity;
import com.spldeolin.beginningmind.core.dao.PermissionDao;
import com.spldeolin.beginningmind.core.dao.User2permissionDao;
import com.spldeolin.beginningmind.core.service.PermissionService;

/**
 * 权限
 *
 * @author Deolin 2018/11/15
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private User2permissionDao user2permissionDao;

    @Override
    public List<PermissionEntity> listGrantedPermission(Long userId) {
        List<Long> permissionIds = user2permissionDao.searchPermissionIdByUserId(userId);

        if (permissionIds.size() > 0) {
            return permissionDao.list(permissionIds);
        } else {
            return Lists.newArrayList();
        }
    }

}