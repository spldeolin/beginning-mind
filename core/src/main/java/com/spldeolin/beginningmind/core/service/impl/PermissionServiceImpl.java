package com.spldeolin.beginningmind.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.repository.PermissionRepo;
import com.spldeolin.beginningmind.core.repository.User2permissionRepo;
import com.spldeolin.beginningmind.core.entity.PermissionEntity;
import com.spldeolin.beginningmind.core.service.PermissionService;

/**
 * 权限
 *
 * @author Deolin 2018/11/15
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private User2permissionRepo user2permissionRepo;

    @Override
    public List<PermissionEntity> listGrantedPermission(Long userId) {
        List<Long> permissionIds = user2permissionRepo.searchPermissionIdByUserId(userId);

        if (permissionIds.size() > 0) {
            return permissionRepo.list(permissionIds);
        } else {
            return Lists.newArrayList();
        }
    }

}