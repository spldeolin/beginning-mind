package com.spldeolin.beginningmind.core.service;

import java.util.List;
import com.spldeolin.beginningmind.core.api.CommonService;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.model.SecurityRoles2permissions;

/**
 * “角色与权限的关联”业务
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
public interface SecurityRoles2permissionsService extends CommonService<SecurityRoles2permissions> {

    /**
     * 创建一个“角色与权限的关联”
     * （附带业务校验）
     *
     * @param securityRoles2permissions 待创建“角色与权限的关联”
     * @return 自增ID
     */
    Long createEX(SecurityRoles2permissions securityRoles2permissions);

    /**
     * 更新一个“角色与权限的关联”
     * （附带业务校验）
     *
     * @param securityRoles2permissions 待更新“角色与权限的关联”
     */
    void updateEX(SecurityRoles2permissions securityRoles2permissions);

    /**
     * 删除一个“角色与权限的关联”
     *
     * @param id 待删除“角色与权限的关联”的ID
     */
    void deleteEX(Long id);

    /**
     * 删除多个资源
     * （附带业务校验，并返回详细情况）
     *
     * @param ids 待删除资源的ID列表
     * @return 删除情况
     */
    String deleteEX(List<Long> ids);

    /**
     * 分页获取资源
     *
     * @param pageNo 页码
     * @param pageSize 分页尺寸
     * @return Page 分页对象
     */
    Page<SecurityRoles2permissions> page(Integer pageNo, Integer pageSize); // 根据具体需求拓展这个方法（追加搜索用参数等）

    // 其他方法声明

}