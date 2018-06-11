package com.spldeolin.beginningmind.core.service;

import java.util.List;
import com.spldeolin.beginningmind.core.api.CommonService;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.model.SecurityRole;

/**
 * “角色”业务
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
public interface SecurityRoleService extends CommonService<SecurityRole> {

    /**
     * 创建一个“角色”
     * （附带业务校验）
     *
     * @param securityRole 待创建“角色”
     * @return 自增ID
     */
    Long createEX(SecurityRole securityRole);

    /**
     * 获取一个“角色”
     *
     * @param id “角色”的ID
     * @return “角色”
     */
    SecurityRole getEX(Long id);

    /**
     * 更新一个“角色”
     * （附带业务校验）
     *
     * @param securityRole 待更新“角色”
     */
    void updateEX(SecurityRole securityRole);

    /**
     * 删除一个“角色”
     *
     * @param id 待删除“角色”的ID
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
    Page<SecurityRole> page(Integer pageNo, Integer pageSize); // 根据具体需求拓展这个方法（追加搜索用参数等）

    // 其他方法声明

}