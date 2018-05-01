package com.spldeolin.beginningmind.service;

import java.util.List;
import com.spldeolin.beginningmind.model.Role;
import com.spldeolin.beginningmind.api.CommonService;
import com.spldeolin.beginningmind.api.dto.Page;

/**
 * “角色”业务
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
public interface RoleService extends CommonService<Role> {

    /**
     * 创建一个“角色”
     * （附带业务校验）
     *
     * @param role 待创建“角色”
     * @return 自增ID
     */
    Long createEX(Role role);

    /**
     * 更新一个“角色”
     * （附带业务校验）
     *
     * @param role 待更新“角色”
     */
    void updateEX(Role role);

    /**
     * 删除一个“角色”
     * 
     *
     * @param id 待删除“角色”的ID
     */
    void deleteEX(Long id);

    /**
     * 删除多个资源
     （附带业务校验，并返回详细情况）
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
    Page<Role> page(Integer pageNo, Integer pageSize); // 根据具体需求拓展这个方法（追加搜索用参数等）

	// 其他方法声明

}