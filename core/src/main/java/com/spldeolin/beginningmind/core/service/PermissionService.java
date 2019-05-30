package com.spldeolin.beginningmind.core.service;

import java.util.List;
import com.spldeolin.beginningmind.core.entity.PermissionEntity;

/**
 * 权限
 *
 * @author Deolin 2018/11/15
 */
public interface PermissionService {

    List<PermissionEntity> listGrantedPermission(Long userId);

}