package com.spldeolin.beginningmind.service;

import java.util.List;
import com.spldeolin.beginningmind.api.CommonService;
import com.spldeolin.beginningmind.api.dto.Page;
import com.spldeolin.beginningmind.model.SecurityAccounts2roles;

/**
 * “帐号与权限的关联”业务
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
public interface SecurityAccounts2rolesService extends CommonService<SecurityAccounts2roles> {

    /**
     * 创建一个“帐号与权限的关联”
     * （附带业务校验）
     *
     * @param securityAccounts2roles 待创建“帐号与权限的关联”
     * @return 自增ID
     */
    Long createEX(SecurityAccounts2roles securityAccounts2roles);

    /**
     * 更新一个“帐号与权限的关联”
     * （附带业务校验）
     *
     * @param securityAccounts2roles 待更新“帐号与权限的关联”
     */
    void updateEX(SecurityAccounts2roles securityAccounts2roles);

    /**
     * 删除一个“帐号与权限的关联”
     *
     * @param id 待删除“帐号与权限的关联”的ID
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
    Page<SecurityAccounts2roles> page(Integer pageNo, Integer pageSize); // 根据具体需求拓展这个方法（追加搜索用参数等）

    // 其他方法声明

}