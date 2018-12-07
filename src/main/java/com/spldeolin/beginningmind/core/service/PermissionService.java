package com.spldeolin.beginningmind.core.service;

import java.util.List;
import com.spldeolin.beginningmind.core.api.CommonService;
import com.spldeolin.beginningmind.core.model.Permission;

/**
 * 权限
 *
 * @author Deolin 2018/11/15
 */
public interface PermissionService extends CommonService<Permission> {

    List<Permission> listGrantedPermission(Long userId);

}