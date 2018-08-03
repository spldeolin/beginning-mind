/*
 * Generated by Cadeau Support.
 *
 * https://github.com/spldeolin/cadeau-support
 */

package com.spldeolin.beginningmind.core.service;

import java.util.List;
import com.spldeolin.beginningmind.core.api.CommonService;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.model.SecurityUsers2roles;

/**
 * “用户与权限的关联”业务
 *
 * @author Deolin 2018/5/28
 */
public interface SecurityUsers2rolesService extends CommonService<SecurityUsers2roles> {

    /**
     * 创建一个“用户与权限的关联” （附带业务校验）
     *
     * @param securityUsers2roles 待创建“用户与权限的关联”
     * @return 自增ID
     */
    Long createEX(SecurityUsers2roles securityUsers2roles);

    /**
     * 更新一个“用户与权限的关联” （附带业务校验）
     *
     * @param securityUsers2roles 待更新“用户与权限的关联”
     */
    void updateEX(SecurityUsers2roles securityUsers2roles);

    /**
     * 删除一个“用户与权限的关联”
     *
     * @param id 待删除“用户与权限的关联”的ID
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
    Page<SecurityUsers2roles> page(Integer pageNo, Integer pageSize); // 根据具体需求拓展这个方法（追加搜索用参数等）

    // 其他方法声明

}