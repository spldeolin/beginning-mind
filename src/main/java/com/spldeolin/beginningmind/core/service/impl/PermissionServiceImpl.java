package com.spldeolin.beginningmind.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.api.CommonServiceImpl;
import com.spldeolin.beginningmind.core.model.Permission;
import com.spldeolin.beginningmind.core.model.User2permission;
import com.spldeolin.beginningmind.core.service.PermissionService;
import com.spldeolin.beginningmind.core.service.User2permissionService;

/**
 * 权限
 *
 * @author Deolin 2018/11/15
 */
@Service
public class PermissionServiceImpl extends CommonServiceImpl<Permission> implements PermissionService {

    @Autowired
    private User2permissionService user2permissionService;


    @Override
    public List<Permission> listGrantedPermission(Long userId) {
        List<Long> permissionIds = user2permissionService.searchBatch("user_id", userId).stream()
                .map(User2permission::getPermissionId).collect(Collectors.toList());

        return Lists.newArrayList(super.listByIds(permissionIds));
    }

}