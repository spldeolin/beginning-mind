package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.entity.PermissionEntity;
import com.spldeolin.beginningmind.mapper.PermissionMapper;
import com.spldeolin.beginningmind.mapper.User2permissionMapper;
import com.spldeolin.beginningmind.service.PermissionService;

/**
 * 权限
 *
 * @author Deolin 2018/11/15
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private User2permissionMapper user2permissionMapper;

    @Override
    public List<PermissionEntity> listGrantedPermission(Long userId) {
        List<Long> permissionIds = user2permissionMapper.listPermissionsByUsers(userId);

        if (permissionIds.size() > 0) {
            return permissionMapper.selectBatchIds(permissionIds);
        } else {
            return Lists.newArrayList();
        }
    }

}