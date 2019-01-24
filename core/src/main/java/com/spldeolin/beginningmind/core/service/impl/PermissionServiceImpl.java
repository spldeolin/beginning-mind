package com.spldeolin.beginningmind.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.common.CommonServiceImpl;
import com.spldeolin.beginningmind.core.entity.PermissionEntity;
import com.spldeolin.beginningmind.core.entity.User2permissionEntity;
import com.spldeolin.beginningmind.core.service.PermissionService;
import com.spldeolin.beginningmind.core.service.User2permissionService;

/**
 * 权限
 *
 * @author Deolin 2018/11/15
 */
@Service
public class PermissionServiceImpl extends CommonServiceImpl<PermissionEntity> implements PermissionService {

    @Autowired
    private User2permissionService user2permissionService;

    @Override
    public List<PermissionEntity> listGrantedPermission(Long userId) {
        List<Long> permissionIds = user2permissionService.searchBatch(User2permissionEntity::getUserId, userId).stream()
                .map(User2permissionEntity::getPermissionId).collect(Collectors.toList());

        List<PermissionEntity> permissions;
        if (permissionIds.size() > 0) {
            permissions = list(permissionIds);
        } else {
            permissions = Lists.newArrayList();
        }
        return permissions;
    }

}