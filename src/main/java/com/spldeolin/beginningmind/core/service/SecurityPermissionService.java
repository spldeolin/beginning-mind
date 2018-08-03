package com.spldeolin.beginningmind.core.service;

import java.util.List;
import com.spldeolin.beginningmind.core.api.CommonService;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.model.SecurityPermission;

/**
 * “权限”业务
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
public interface SecurityPermissionService extends CommonService<SecurityPermission> {

    /**
     * 创建一个“权限” （附带业务校验）
     *
     * @param securityPermission 待创建“权限”
     * @return 自增ID
     */
    Long createEX(SecurityPermission securityPermission);

    /**
     * 更新一个“权限” （附带业务校验）
     *
     * @param securityPermission 待更新“权限”
     */
    void updateEX(SecurityPermission securityPermission);

    /**
     * 删除一个“权限”
     *
     * @param id 待删除“权限”的ID
     */
    void deleteEX(Long id);

    /**
     * 删除多个资源 （附带业务校验，并返回详细情况）
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
    Page<SecurityPermission> page(Integer pageNo, Integer pageSize); // 根据具体需求拓展这个方法（追加搜索用参数等）

    // 其他方法声明

}